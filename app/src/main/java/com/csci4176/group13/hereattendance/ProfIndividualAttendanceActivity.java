package com.csci4176.group13.hereattendance;

import android.content.Intent;
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
import android.view.MenuItem;
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

    // we need this to calculate the attendance for a given day
    int numStudents = 12;

    // this is the number that will be displayed BEFORE the database pull changes the text
    double attendedPercent = 0;

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
            courseCode = getIntent().getStringExtra("courseCode");
        }
        setTitle(lectureNum);

        // Setting up the RecyclerView (list)
        RecyclerView rv = findViewById(R.id.rv);
        rv.setHasFixedSize(false);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        // pulling from the database to see if student attended this lecture
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    //if this snapshot is for the right course
                    if (snapshot.getKey().equals(courseCode)) {
                        for (DataSnapshot courseLecture : snapshot.getChildren()) {

                            // if this snapshot is for the right lecture
                            if (courseLecture.getKey().equals("" + lectureNum.charAt(8)))
                                if (courseLecture.hasChild("student")) {

                                    // add student to arraylist
                                    studentAttendance.add(new IndividualStudentAttendance("Appleseed, John", true));

                                    // adjust percent attendance to reflect and additional student being there
                                    attendedPercent=(attendedPercent*numStudents + 1)/(numStudents+1);
                                    percentage.setText((int)attendedPercent+"%");

                                } else {
                                    studentAttendance.add(new IndividualStudentAttendance("Appleseed, John", false));
                                    attendedPercent=(attendedPercent*numStudents)/(numStudents+1);
                                    percentage.setText((int)attendedPercent+"%");
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

        // In a normal setting this would not be hardcoded and would be pulled from the database
        // This just switches up who attended and who didn't. It adds variety rather than
        // showing that 65% attended during every lecture, etc
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
            // Again, each course has different students
            // this if/else calculates the attendance percent based on the boolean value we just set
            numStudents = 12;

            if (!attended)
                attendedPercent = (9.0 * 100.0 / numStudents);
            else
                attendedPercent = (2.0 * 100.0 / numStudents);

            studentAttendance.add(new IndividualStudentAttendance("Abarbanel, Sarah", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Balboa, Porky", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Bistekos, Joanna", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Campbell, Susan", attended));
            studentAttendance.add(new IndividualStudentAttendance("Daniels, Daniel", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Negahban, Abdullah", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Shah, Dhruvi", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Shah, Sunitkumar", attended));
            studentAttendance.add(new IndividualStudentAttendance("Conchita, Truelove", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Lorelei, Cleavenger", !attended));
            studentAttendance.add(new IndividualStudentAttendance("Fidel, Vo", !attended));
        }
        if (courseCode.equals("CSCI3130")) {
            numStudents = 14;

            if (attended)
                attendedPercent = (3.0 * 100.0 / numStudents);
            else
                attendedPercent = (10.0 * 100.0 / numStudents);

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
            numStudents = 14;

            if (attended)
                attendedPercent = (3.0 * 100.0 / numStudents);
            else
                attendedPercent = (10.0 * 100.0 / numStudents);

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
        percentage.setText((int)attendedPercent+"%");
        rv.setAdapter(adapter);
    }

    /**
     * Sending data back on back button press
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("courseCode", courseCode);
        setResult(RESULT_OK, intent);
        finish();
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

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}