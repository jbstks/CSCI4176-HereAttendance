package com.csci4176.group13.hereattendance.Fragments.Student;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.csci4176.group13.hereattendance.MainActivity;
import com.csci4176.group13.hereattendance.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

/**
 * Fragment for the QR camera scanner
 */
public class QRScannerFragment extends android.support.v4.app.Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    CameraSource camera;
    SurfaceView qrCodeView;
    //TextView qrResult;
    BarcodeDetector qrDetect;
    int cameraPermission = 007;
    int validFormat = 0; // 0 for valid, 1 for not able to update, 2 for invalid


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference DatabaseUpdateRef = database.getReference();

    public QRScannerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_qrscanner, container, false);
        qrCodeView = view.findViewById(R.id.QRView);
        //qrResult = view.findViewById(R.id.txt);
        qrDetect = new BarcodeDetector.Builder(view.getContext())
                .setBarcodeFormats(Barcode.QR_CODE).build();
        camera = new CameraSource.Builder(view.getContext(), qrDetect).build();

        qrCodeView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if(ActivityCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{android.Manifest.permission.CAMERA}, cameraPermission);
                    return;
                }
                try {
                    camera.start(qrCodeView.getHolder());
                }catch (IOException e){
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
                if(codes.size() != 0){
                    String[] attendanceInfo =codes.valueAt(0).displayValue.split(" ");
                    if(attendanceInfo.length<4)
                        validFormat=2;
                    if(!attendanceInfo[0].matches("CSCI[0-9]..."))
                        validFormat=2;
                    if(!attendanceInfo[3].matches("[0-9].*"))
                        validFormat=2;
                    DatabaseUpdateRef.child(attendanceInfo[0]).child(attendanceInfo[3]).child("date").setValue(attendanceInfo[1]+" "+attendanceInfo[2]).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            validFormat=0;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            validFormat=1;
                        }
                    });
                    DatabaseUpdateRef.child(attendanceInfo[0]).child(attendanceInfo[3]).child("student").setValue("true").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            validFormat=0;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            validFormat=1;
                        }
                    });


                    qrCodeView.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrate = (Vibrator)getActivity().getApplicationContext()
                                    .getSystemService(Context.VIBRATOR_SERVICE);
                            vibrate.vibrate(100);
                            showAlertDialogButtonClicked(codes.valueAt(0).displayValue);
                            release();
                        }
                    });
                }
            }
        });

        return view;
    }

        public void showAlertDialogButtonClicked(String course) {

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
}
            // backend note that the course was scanned
            Log.d("QR SCAN", course+" has been scanned");

            DialogInterface.OnClickListener dialogButtonClick =
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch(which){
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
            //show the alert dialog
            dialog.show();
        }
}