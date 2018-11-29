package com.csci4176.group13.hereattendance.Fragments.Student;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
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
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

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
            builder.setTitle("Scan Successful");
            builder.setMessage("Attendance for " +course+" has been registered.");
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