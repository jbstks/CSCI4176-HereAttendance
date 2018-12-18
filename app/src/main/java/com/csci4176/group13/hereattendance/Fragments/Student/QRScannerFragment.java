package com.csci4176.group13.hereattendance.Fragments.Student;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.csci4176.group13.hereattendance.MainActivity;
import com.csci4176.group13.hereattendance.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Fragment for the QR camera scanner
 */
public class QRScannerFragment extends android.support.v4.app.Fragment {

    CameraSource camera;
    SurfaceView qrCodeView;
    BarcodeDetector qrDetect;
    int cameraPermission = 007;
    int validFormat = 0; // 0 for valid, 1 for not able to update, 2 for invalid
    double distance = 0;
    double uLat = 0;
    double uLong = 0;
    String building;
    LatLng CS = new LatLng(44.637444, -63.587224);
    LatLng LSC = new LatLng(44.636228, -63.594058);
    LatLng Tupper = new LatLng(44.639354, -63.583841);
    double r = 1;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference DatabaseUpdateRef = database.getReference();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public QRScannerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_qrscanner, container, false);
        qrCodeView = view.findViewById(R.id.QRView);
        qrDetect = new BarcodeDetector.Builder(view.getContext())
                .setBarcodeFormats(Barcode.QR_CODE).build();
        camera = new CameraSource.Builder(view.getContext(), qrDetect).build();

        qrCodeView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{android.Manifest.permission.CAMERA}, cameraPermission);
                    return;
                }
                try {
                    camera.start(qrCodeView.getHolder());
                    LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    Location location = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    if(location != null) {
                        uLat = location.getLatitude();
                        uLong = location.getLongitude();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                camera.stop();
            }
        });

        qrDetect.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                camera.stop();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> codes = detections.getDetectedItems();
                if (codes.size() != 0) {


                    final String[] attendanceInfo = codes.valueAt(0).displayValue.split(" ");
                    if (attendanceInfo.length < 4 ||
                            !attendanceInfo[0].matches("CSCI[0-9]...") ||
                            !attendanceInfo[3].matches("[0-9].*")) {

                        qrCodeView.post(new Runnable() {
                            @Override
                            public void run() {
                                Vibrator vibrate = (Vibrator) getActivity().getApplicationContext()
                                        .getSystemService(Context.VIBRATOR_SERVICE);
                                vibrate.vibrate(100);
                                showAlertDialogButtonClicked("TheCourse", 2);
                                release();
                            }
                        });
                    } else {
                        Log.d("COURSEABDULLAH","Course name"+codes.valueAt(0).displayValue.substring(0,8));
                        if (codes.valueAt(0).displayValue.substring(0,8).equals("CSCI4176")) {
                            building = "CS";
                            distance = Math.acos((Math.sin(Math.toRadians(uLat))*Math.sin(Math.toRadians(CS.latitude)))+
                                    (Math.cos(Math.toRadians(uLat))*Math.cos(Math.toRadians(CS.latitude))*
                                            Math.cos(Math.toRadians(uLong)-Math.toRadians(CS.longitude))));
                            //Log.d("IF1", "if1"+distance);
                        } else if (codes.valueAt(0).displayValue.substring(0,8).equals("CSCI3130")) {
                            building = "LSC";
                            distance = Math.acos((Math.sin(Math.toRadians(uLat))*Math.sin(Math.toRadians(LSC.latitude)))+
                                    (Math.cos(Math.toRadians(uLat))*Math.cos(Math.toRadians(LSC.latitude))*
                                            Math.cos(Math.toRadians(uLong)-Math.toRadians(LSC.longitude))));
                            //Log.d("IF2", "if1"+distance);
                        } else if (codes.valueAt(0).displayValue.substring(0,8).equals("CSCI3110")) {
                            building = "Tupper";
                            distance = Math.acos((Math.sin(Math.toRadians(uLat))*Math.sin(Math.toRadians(Tupper.latitude)))+
                                    (Math.cos(Math.toRadians(uLat))*Math.cos(Math.toRadians(Tupper.latitude))*
                                            Math.cos(Math.toRadians(uLong)-Math.toRadians(Tupper.longitude))))*6371;
                            //Log.d("IF3", "if1"+distance);
                        }

                        if (distance <= 0.200){
                            DatabaseUpdateRef.child(attendanceInfo[0]).child(attendanceInfo[3]).child("date").setValue(attendanceInfo[1] + " " + attendanceInfo[2]);
                        DatabaseUpdateRef.child(attendanceInfo[0]).child(attendanceInfo[3]).child("student").setValue("true").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Toast.makeText(getContext(), "distance"+distance, Toast.LENGTH_LONG).show();
                                validFormat = 0;
                                qrCodeView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Vibrator vibrate = (Vibrator) getActivity().getApplicationContext()
                                                .getSystemService(Context.VIBRATOR_SERVICE);
                                        vibrate.vibrate(100);
                                        showAlertDialogButtonClicked(codes.valueAt(0).displayValue, 0);

                                    }
                                });
                                release();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                validFormat = 1;
                                qrCodeView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Vibrator vibrate = (Vibrator) getActivity().getApplicationContext()
                                                .getSystemService(Context.VIBRATOR_SERVICE);
                                        vibrate.vibrate(100);
                                        showAlertDialogButtonClicked(codes.valueAt(0).displayValue, 1);

                                    }
                                });
                                release();
                            }
                        });
                    }
                    else{
                            validFormat = 3;
                            qrCodeView.post(new Runnable() {
                                @Override
                                public void run() {
                                    Vibrator vibrate = (Vibrator) getActivity().getApplicationContext()
                                            .getSystemService(Context.VIBRATOR_SERVICE);
                                    vibrate.vibrate(100);
                                    showAlertDialogButtonClicked(codes.valueAt(0).displayValue, 3);
                                    release();

                                }
                            });
                            //release();
                        }
                    }
                }
            }
        });

        return view;
    }

    public void showAlertDialogButtonClicked(String course, int validFormat) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        switch (validFormat) {
            case 0:
                builder.setTitle("Scan Successful");
                builder.setMessage("Attendance for " + course.substring(0, 8) + " has been registered.");
                break;
            case 1:
                builder.setTitle("Update Failed");
                builder.setMessage("Unable to update attendance. Please check network connection");
                break;
            case 2:
                builder.setTitle("Scan Failed");
                builder.setMessage("The QR code scanned is improperly formatted");
                break;
            case 3:
                builder.setTitle("Scan Failed");
                builder.setMessage("Please scan the QR code from within the lecture room");
                break;
        }
        // backend note that the course was scanned
        Log.d("QR SCAN", course + " has been scanned");

        DialogInterface.OnClickListener dialogButtonClick =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                break;
                        }
                    }
                };

        // add a button
        builder.setPositiveButton("OK", dialogButtonClick);
        // create the alert dialog
        AlertDialog dialog = builder.create();
        // show the alert dialog
        dialog.show();
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(getActivity(), new String[] {ACCESS_FINE_LOCATION}, 1);
    }
}