package com.csci4176.group13.hereattendance;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * Activity for the QR generator
 *
 * @author Joanna Bistekos
 * @author Dhruvi Shah
 * @author Sunitkumar Shah
 */
public class QRGeneratorActivity extends AppCompatActivity {

    // UI references and value holders
    ImageView imgQRCode;
    String courseCode;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_qrgenerator);

        // Setting the action bar functionality
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_close_24px);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgQRCode = findViewById(R.id.imgQRCode);

        // Getting course code passed through intent
        if (getIntent().getExtras() != null) {
            courseCode = getIntent().getStringExtra("courseCode");
        }
        setTitle(courseCode+" QR Code");

        // Attempt to generate a QR code
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            String QRText = courseCode+"_"+date;
            Toast.makeText(getApplicationContext(), QRText, Toast.LENGTH_SHORT).show();

            // Generating QR Code For QRText
            BitMatrix bitMatrix = multiFormatWriter.encode(QRText, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

            // Setting QR Code to ImageView
            imgQRCode.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}