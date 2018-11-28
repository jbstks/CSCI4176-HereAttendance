package com.csci4176.group13.hereattendance.Fragments.Student;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.csci4176.group13.hereattendance.AttendanceData.ClassAttendanceRVAdapter;
import com.csci4176.group13.hereattendance.AttendanceData.LectureAttendance;
import com.csci4176.group13.hereattendance.CurrentCourses.CurrCoursesRVAdapter;
import com.csci4176.group13.hereattendance.CurrentCourses.CurrentCourse;
import com.csci4176.group13.hereattendance.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment to hold student's list values of current courses
 */
public class StudentAttendanceHistoryFragment extends Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    DatabaseReference myRef = database.getReference();
    CurrCoursesRVAdapter adapter;
    int[] gradesarray = {0, 0, 0, 0};

    public StudentAttendanceHistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_history, container, false);
        RecyclerView rv = view.findViewById(R.id.rv);
        rv.setHasFixedSize(false);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        final List<CurrentCourse> currentCourses;
        currentCourses = new ArrayList<>();

        currentCourses.add(new CurrentCourse("CSCI3130", "Software Engineering", gradesarray[0]));
        currentCourses.add(new CurrentCourse("CSCI3110", "Algorithms", gradesarray[2]));
        currentCourses.add(new CurrentCourse("CSCI3130", "Software Engineering", gradesarray[0]));
        currentCourses.add(new CurrentCourse("CSCI4176", "Mobile Computing", gradesarray[1]));


        adapter = new CurrCoursesRVAdapter(currentCourses);
        rv.setAdapter(adapter);
        // myRef=myRef.child(getActivity().getIntent().getStringExtra("courseCode"));
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
int lectnum = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int there = 0;
                    int dayspast = 0;
lectnum++;
                    for (DataSnapshot courseLecture : snapshot.getChildren()) {
                        dayspast++;
                        if (courseLecture.hasChild("student")) {
                            there++;
                        }


                    }
                    Log.d("courseCode","ppp"+(there * 100 / dayspast));
                    currentCourses.get(lectnum).setAttendancePercent(there * 100 / dayspast);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Unable to pull from the database. Please check network connection", Toast.LENGTH_LONG).show();
            }
        });
        adapter.notifyDataSetChanged();


        return view;
    }
}
