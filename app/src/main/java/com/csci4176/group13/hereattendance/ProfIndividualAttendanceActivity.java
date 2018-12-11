package com.csci4176.group13.hereattendance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.csci4176.group13.hereattendance.AttendanceData.LectureAttendance;
import com.csci4176.group13.hereattendance.AttendanceData.ProfClassAttendanceRVAdapter;
import com.csci4176.group13.hereattendance.IndividualStudentAttendanceData.IndividualStudentAttendance;
import com.csci4176.group13.hereattendance.IndividualStudentAttendanceData.IndividualStudentAttendanceRVAdapter;
import com.csci4176.group13.hereattendance.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity to hold professors's list of individual student's class attendance
 */
public class ProfIndividualAttendanceActivity extends AppCompatActivity {

    CardView daysAttended;
    String lectureNum;
    String courseCode;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    TextView percentage;
    List<IndividualStudentAttendance> studentAttendance = new ArrayList<>();
    IndividualStudentAttendanceRVAdapter adapter = new IndividualStudentAttendanceRVAdapter(studentAttendance);
    boolean studentAttended = false;
    int numStudents = 12;
    double attendedPercent=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_attendance);
        percentage = findViewById(R.id.overallAttendancePercent);
        // Setting the action bar functionality
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_close_24px);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Getting the intent extras
        if (getIntent().getExtras() != null) {
            lectureNum = getIntent().getStringExtra("lectureNum");
            courseCode = getIntent().getStringExtra("CourseCode");
        }
        setTitle(lectureNum);


        // Setting up the RecyclerView (list)
        RecyclerView rv = findViewById(R.id.rv);
        rv.setHasFixedSize(false);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(courseCode)) {
                        for (DataSnapshot courseLecture : snapshot.getChildren()) {
                            if (courseLecture.getKey().equals("" + lectureNum.charAt(8)))
                                if (courseLecture.hasChild("student")) {
                                    studentAttendance.add(new IndividualStudentAttendance("Appleseed, John", true));
                                    studentAttended = true;
                                    attendedPercent=(attendedPercent*numStudents + 1)/(numStudents+1);
                                    percentage.setText((int)attendedPercent+" %");

                                } else {
                                    studentAttendance.add(new IndividualStudentAttendance("Appleseed, John", false));
                                    attendedPercent=(attendedPercent*numStudents)/(numStudents+1);
                                    percentage.setText((int)attendedPercent+" %");
                                }

                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        // TODO pull database data to change attendance data, also need a way to set the attendance outside of the setting the adapter values, I'll just spoof it
        boolean attended = false;
        switch (lectureNum) {
            case "Lecture 1":
                attended = false;
                break;
            case "Lecture 2":
                attended = true;
                break;
            case "Lecture 3":
                attended = false;
                break;
            case "Lecture 4":
                attended = false;
                break;
            case "Lecture 5":
                attended = true;
                break;
            case "Lecture 6":
                attended = true;
                break;
        }

        if (courseCode.equals("CSCI3110")) {
numStudents=12;
            if (!attended) {
                    attendedPercent=(9.0 * 100.0 / 12.0);
            } else {
                attendedPercent=(2.0 * 100.0 / 12.0);
            }
            studentAttendance.add(new IndividualStudentAttendance("Abarbanel, Sarah", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Appleseed, John", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Bistekos, Joanna", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Campbell, Susan", attended));
            studentAttendance.add(new IndividualStudentAttendance("Daniels, Daniel", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Negahban, Abdullah", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Shah, Dhruvi", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Shah, Sunitkumar", attended));
            studentAttendance.add(new IndividualStudentAttendance("Conchita,Truelove", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Lorelei,Cleavenger", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Fidel,Vo", !attended));
        }
        if (courseCode.equals("CSCI3130")) {
            numStudents=14;

            if (attended) {



                attendedPercent=(3.0 * 100.0 / 14.0);

            } else {

                attendedPercent=(10 * 100 / 14);
            }

            studentAttendance.add(new IndividualStudentAttendance("Bert, Beaver", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Else, Culwell", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Carina, Speece", attended));
            studentAttendance.add(new IndividualStudentAttendance("Ashton, Weitz", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Aleshia, Yerkes", attended));
            studentAttendance.add(new IndividualStudentAttendance("Sara, Kosloski", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Fairy, Tobin", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Adolfo, Meals", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Camille, Ponder", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Nick, Schloss", attended));
            studentAttendance.add(new IndividualStudentAttendance("Conception, Wiser", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Darcie, Horney", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Eveline, Wiemer", !attended));
        }
        if (courseCode.equals("CSCI4176")) {
            numStudents=14;

            if (attended) {
                attendedPercent=(3.0 * 100.0 / 14.0);
            } else {
                attendedPercent=(10 * 100 / 14);
            }

            studentAttendance.add(new IndividualStudentAttendance("Mi, Welle", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Berenice, Augustus", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Mikel, Nusbaum", attended));
            studentAttendance.add(new IndividualStudentAttendance("Carlos, Clow", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Barbie, Schmitmeyer", attended));
            studentAttendance.add(new IndividualStudentAttendance("Gisele, Vanderveen", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Desire, Courville", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Wilton, Lipson", attended));
            studentAttendance.add(new IndividualStudentAttendance("Sheridan, Henrich", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Karolyn, Soo", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Darnell, Bye", attended));
            studentAttendance.add(new IndividualStudentAttendance("Nannette, Zilnicki", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Vi, Hoelscher", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Chia, Luera", !attended));
        }
        percentage.setText((int)attendedPercent+" %");
        rv.setAdapter(adapter);
    }
}
