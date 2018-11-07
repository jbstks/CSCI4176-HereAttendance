package com.csci4176.group13.hereattendance.Fragments.Student;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csci4176.group13.hereattendance.AttendanceData.LectureAttendance;
import com.csci4176.group13.hereattendance.AttendanceData.ClassAttendanceRVAdapter;
import com.csci4176.group13.hereattendance.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment to hold student's list values of class attendance
 */
public class StudentClassAttendanceFragment extends Fragment {

    TextView overallAttendancePercent;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StudentClassAttendanceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_attendance, container, false);

        RecyclerView rv = view.findViewById(R.id.rv);
        rv.setHasFixedSize(false);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        List<LectureAttendance> studentAttendance;
        studentAttendance = new ArrayList<>();

        studentAttendance.add(new LectureAttendance(1, "September 4", true));
        studentAttendance.add(new LectureAttendance(2, "September 8", true));
        studentAttendance.add(new LectureAttendance(3, "September 12", true));
        studentAttendance.add(new LectureAttendance(4, "September 14", false));
        ClassAttendanceRVAdapter adapter = new ClassAttendanceRVAdapter(studentAttendance);
        rv.setAdapter(adapter);

        /*overallAttendancePercent = view.findViewById(R.id.overallAttendancePercent);
        overallAttendancePercent.setText();*/

        return view;
    }
}
