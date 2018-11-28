package com.csci4176.group13.hereattendance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Activity for the QR generator
 *
 * Implemented QR sharing similar to to solution found here:
 * https://stackoverflow.com/questions/9049143/android-share-intent-for-a-bitmap-is-it-possible-not-to-save-it-prior-sharing
 * as sharing requires saving the image (which requires permissions), and we wanted to avoid this.
 *
 * This method is preferred as we do not need to ask for write permissions, and it will not take an
 * abundance of space on the device.
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
    Bitmap bitmap;

    /**
     * Things to be done on activity creation
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerator);

        // Setting the action bar functionality
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_close_24px);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgQRCode = findViewById(R.id.imgQRCode);

        // Getting course code passed through intent
        if (getIntent().getExtras() != null) {
            courseCode = getIntent().getStringExtra("courseCode");
            date = getIntent().getStringExtra("date");
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
            bitmap = barcodeEncoder.createBitmap(bitMatrix);

            // Setting QR Code to ImageView
            imgQRCode.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Try to save the bitmap into the cache so that we can share it
        try {

            File cachePath = new File(getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            // This image gets overwritten every time the QR code is generated
            FileOutputStream stream = new FileOutputStream(cachePath + "/qrcode.png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present
     *
     * @param menu
     * @return true if successful
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.qrgenerator_share, menu);
        return true;
    }

    /**
     * Handle action bar item clicks here. The action bar will automatically handle clicks on
     * the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
     *
     * @param item
     * @return MenuItem selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            File imagePath = new File(getCacheDir(), "images");
            File generatedQR = new File(imagePath, "qrcode.png");
            Uri imageURI = FileProvider.getUriForFile(this, "com.csci4176.group13.hereattendance", generatedQR);

            if (imageURI != null) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageURI);
                shareIntent.setType("image/png");
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}