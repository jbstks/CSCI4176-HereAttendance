package com.csci4176.group13.hereattendance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csci4176.group13.hereattendance.AttendanceData.LectureAttendance;
import com.csci4176.group13.hereattendance.AttendanceData.ProfClassAttendanceRVAdapter;
import com.csci4176.group13.hereattendance.IndividualStudentAttendanceData.IndividualStudentAttendance;
import com.csci4176.group13.hereattendance.IndividualStudentAttendanceData.IndividualStudentAttendanceRVAdapter;
import com.csci4176.group13.hereattendance.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity to hold professors's list of individual student's class attendance
 */
public class ProfIndividualAttendanceActivity extends AppCompatActivity {

    CardView daysAttended;
    String lectureNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_attendance);

        // Setting the action bar functionality
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_close_24px);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Getting the intent extras
        if (getIntent().getExtras() != null)
            lectureNum = getIntent().getStringExtra("lectureNum");
        setTitle(lectureNum);

        // Setting up the RecyclerView (list)
        RecyclerView rv = findViewById(R.id.rv);
        rv.setHasFixedSize(false);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        List<IndividualStudentAttendance> studentAttendance;
        studentAttendance = new ArrayList<>();

        studentAttendance.add(new IndividualStudentAttendance("Abarbanel, Sarah", true));
        studentAttendance.add(new IndividualStudentAttendance("Appleseed, John", true));
        studentAttendance.add(new IndividualStudentAttendance("Bistekos, Joanna", true));
        studentAttendance.add(new IndividualStudentAttendance("Campbell, Susan", false));
        studentAttendance.add(new IndividualStudentAttendance("Daniels, Daniel", true));
        studentAttendance.add(new IndividualStudentAttendance("Negahban, Abdullah", true));
        studentAttendance.add(new IndividualStudentAttendance("Shah, Dhruvi", true));
        studentAttendance.add(new IndividualStudentAttendance("Shah, Sunitkumar", false));
        IndividualStudentAttendanceRVAdapter adapter = new IndividualStudentAttendanceRVAdapter(studentAttendance);
        rv.setAdapter(adapter);
    }
}
