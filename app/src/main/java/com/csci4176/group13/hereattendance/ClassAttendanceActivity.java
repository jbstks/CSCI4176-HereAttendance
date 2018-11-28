package com.csci4176.group13.hereattendance;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.csci4176.group13.hereattendance.Fragments.Professor.ProfClassAttendanceFragment;
import com.csci4176.group13.hereattendance.Fragments.Student.StudentClassAttendanceFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ClassAttendanceActivity extends AppCompatActivity {

    FirebaseUser signedInUser = FirebaseAuth.getInstance().getCurrentUser();
    String user;

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

}
