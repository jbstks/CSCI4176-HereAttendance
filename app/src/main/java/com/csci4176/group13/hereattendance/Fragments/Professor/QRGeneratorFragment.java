package com.csci4176.group13.hereattendance.Fragments.Professor;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.csci4176.group13.hereattendance.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * Fragment for the QR generator
 *
 * TODO: Delete this
 */
public class QRGeneratorFragment extends android.support.v4.app.Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public QRGeneratorFragment() { }

    Button btnQRGenerate;
    EditText edtQRCSCIText,edtQRDateText;
    ImageView imgQRCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         *Getting the layout for QR Generator
         */
        View view = inflater.inflate(R.layout.fragment_qrgenerator, container, false);

        btnQRGenerate = view.findViewById(R.id.btnQRGenerate);
        edtQRCSCIText = view.findViewById(R.id.edtQRCSCIText);
        edtQRDateText = view.findViewById(R.id.edtQRDateText);
        imgQRCode = view.findViewById(R.id.imgQRCode);

            // OnClickListener for generating QR code
            btnQRGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    String strCSCI = edtQRCSCIText.getText().toString();
                    String strDateLect = edtQRDateText.getText().toString();
                    String QRText = strCSCI.concat("_"+strDateLect);
                    Toast.makeText(getContext(),QRText,Toast.LENGTH_SHORT).show();

                    // Generating QR Code For QRText
                    BitMatrix bitMatrix = multiFormatWriter.encode(QRText, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                    // setting QR Code to ImageView
                    imgQRCode.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
}