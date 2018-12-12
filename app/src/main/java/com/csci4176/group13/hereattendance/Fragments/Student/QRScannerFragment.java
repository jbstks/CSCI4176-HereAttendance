package com.csci4176.group13.hereattendance.Fragments.Student;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.csci4176.group13.hereattendance.MainActivity;
import com.csci4176.group13.hereattendance.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

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
    TextView qrResult;
    BarcodeDetector qrDetect;

    //Permission RequestCode
    int cameraPermission = 007;
    int locationPermission = 005;

    //
    LocationManager locationManager;
    Location UserLoction =null;


    public QRScannerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_qrscanner, container, false);
        qrCodeView = view.findViewById(R.id.QRView);
        qrResult = view.findViewById(R.id.txt);
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
                    qrResult.post(new Runnable() {
                        @Override
                        public void run() {

                            Address ClassAddress = getLatLongForClass("211, Goldberg Building,Dalhousie University,Halifax,Nova Scotia");

                            if (InClass(ClassAddress)) {
                                Vibrator vibrate = (Vibrator) getActivity().getApplicationContext()
                                        .getSystemService(Context.VIBRATOR_SERVICE);
                                vibrate.vibrate(100);
                                showAlertDialogButtonClicked(codes.valueAt(0).displayValue);
                                release();
                            } else {
                                showAlertDialogButtonClicked(null);
                                release();
                            }
                        }
                    });
                }
            }
        });
        return view;
    }


    //Check if the Student is in class or not
    public boolean InClass(Address address)
    {
        boolean InClassRange = false;
        locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);


        //LocationListener to fetch location on location change
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("LATLONG", "Location" + location.toString());
                if(location != null)
                    UserLoction = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        //check if permission is granted or not
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            //Request permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, locationPermission);

            if (permissionGranted()) {
                //receive Location Updates
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10, locationListener);
            }
        }
        else
        {
                // if permission is already granted then receive Location updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10, locationListener);
        }

        float distanceResults[] = new float[]{};
    if (UserLoction != null && address != null)
        Location.distanceBetween(UserLoction.getLatitude(),UserLoction.getLongitude(),address.getLatitude(),address.getLongitude(),distanceResults);
        // get distance result and decide according to result if it is range of class or not then pass boolean accordingly


        return  InClassRange;
    }



    //From Address of class get its LatLong
    public Address getLatLongForClass(String ClassLocation) {
        Geocoder coder = new Geocoder(this.getContext());
        List<Address> address;
        Address location;
        try {
            address = coder.getFromLocationName(ClassLocation, 5);
            location = address.get(0);
            return location;

        } catch (IOException e) {
            e.printStackTrace();
            location = null;
            return location;
        }
    }

    //Check if Permission is granted or not
    public boolean permissionGranted()
    {
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        Log.d("LATLONG","The permission granted ");


        return false;
    }





        public void showAlertDialogButtonClicked(String course) {

            // setup the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("ALERT");
            if(course!= null)
            builder.setMessage("Attendance for " +course+" has been registered");
            else
                builder.setMessage("Your Attendance is not marked for the class ");

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