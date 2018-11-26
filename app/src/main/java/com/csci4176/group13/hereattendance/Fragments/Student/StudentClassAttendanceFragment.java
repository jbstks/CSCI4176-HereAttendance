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
import android.widget.TextView;
import android.widget.Toast;

import com.csci4176.group13.hereattendance.AttendanceData.LectureAttendance;
import com.csci4176.group13.hereattendance.AttendanceData.ClassAttendanceRVAdapter;
//import com.csci4176.group13.hereattendance.DatabaseSingleton;
import com.csci4176.group13.hereattendance.DatabaseSingleton;
import com.csci4176.group13.hereattendance.LoginActivity;
import com.csci4176.group13.hereattendance.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment to hold student's list values of class attendance
 */

// TODO figure out why it's not grabbing the right node when it's getting the child
public class StudentClassAttendanceFragment extends Fragment {
    List<LectureAttendance> studentAttendance = new ArrayList<>();

    ClassAttendanceRVAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StudentClassAttendanceFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new ClassAttendanceRVAdapter(studentAttendance);

        View view = inflater.inflate(R.layout.fragment_class_attendance, container, false);

        if (getActivity().getIntent().getExtras() != null)
            getActivity().setTitle(getActivity().getIntent().getStringExtra("courseCode"));
        RecyclerView rv = view.findViewById(R.id.rv);
        rv.setHasFixedSize(false);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
        studentAttendance = DatabaseSingleton.getStudentLectureAttendance(getActivity().getIntent().getStringExtra("courseCode"));
        Log.d("courseCode",getActivity().getIntent().getStringExtra("courseCode"));

        adapter.notifyDataSetChanged();

        return view;
    }
}
