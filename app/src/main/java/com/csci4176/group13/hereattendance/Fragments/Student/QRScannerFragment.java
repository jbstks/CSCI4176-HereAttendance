package com.csci4176.group13.hereattendance.Fragments.Student;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.csci4176.group13.hereattendance.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.w3c.dom.Text;

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
    TextView qrResult;
    BarcodeDetector qrDetect;
    int cameraPermission = 007;

    public QRScannerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_qrscanner, container, false);
        qrCodeView = view.findViewById(R.id.QRView);
        qrResult = view.findViewById(R.id.txt);
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

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> codes = detections.getDetectedItems();

                if(codes.size() != 0){
                    qrResult.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrate = (Vibrator)getActivity().getApplicationContext()
                                    .getSystemService(Context.VIBRATOR_SERVICE);
                            vibrate.vibrate(100);
                            Toast.makeText(getContext(),codes.valueAt(0).displayValue,
                                    Toast.LENGTH_SHORT).show();
                            release();
                        }
                    });
                }
            }
        });

        return view;
    }
}