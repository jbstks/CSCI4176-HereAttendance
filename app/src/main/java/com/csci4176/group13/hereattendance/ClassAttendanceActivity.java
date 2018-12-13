package com.csci4176.group13.hereattendance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.csci4176.group13.hereattendance.Fragments.Professor.ProfClassAttendanceFragment;
import com.csci4176.group13.hereattendance.Fragments.Student.StudentClassAttendanceFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;

public class ClassAttendanceActivity extends AppCompatActivity {

    FirebaseUser signedInUser = FirebaseAuth.getInstance().getCurrentUser();
    String user;
    public String courseCode;

    /**
     * Things to be done on activity creation
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_attendance);

        // Check if user is signed in
        if (signedInUser != null)
            user = signedInUser.getEmail();

        // Setting the action bar functionality
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_close_24px);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        // Show/hide content depending on user
        if ( user.equals("professor@here.com") )
            fm.beginTransaction().replace(R.id.content, new ProfClassAttendanceFragment()).commit();
        else
            fm.beginTransaction().replace(R.id.content, new StudentClassAttendanceFragment()).commit();
    }

    /**
     * Getting data back from the 2nd activity
     *
     * @param requestCode the code requested (ie, what you're looking for)
     * @param resultCode the code sent back
     * @param data the Intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                courseCode = data.getStringExtra("courseCode");
            }
        }
    }
}
