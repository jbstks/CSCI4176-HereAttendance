package com.csci4176.group13.hereattendance.Fragments.Professor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csci4176.group13.hereattendance.AttendanceData.ProfClassAttendanceRVAdapter;
import com.csci4176.group13.hereattendance.AttendanceData.LectureAttendance;
import com.csci4176.group13.hereattendance.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment to hold student's list values of class attendance
 */
public class ProfClassAttendanceFragment extends Fragment {

    CardView daysAttended;
    TextView overallAttendancePercent;
    String courseCode;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProfClassAttendanceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_attendance, container, false);

        // Hide daysAttended because it should only be seen by a student
        daysAttended = (CardView) view.findViewById(R.id.daysAttended);
        daysAttended.setVisibility(View.GONE);
        overallAttendancePercent = (TextView) view.findViewById(R.id.overallAttendancePercent);


        if (getActivity().getIntent().getExtras() != null)
            courseCode = getActivity().getIntent().getStringExtra("courseCode");
        getActivity().setTitle(courseCode);

        // approx total course attendance
        switch (courseCode) {
            case ("CSCI3110"):
                overallAttendancePercent.setText(8 * 100 / 12 + "%");
                break;
            case ("CSCI3130"):
                overallAttendancePercent.setText(9 * 100 / 14 + "%");
                break;
            case ("CSCI4176"):
                overallAttendancePercent.setText(8 * 100 / 14 + "%");
                break;
        }

        RecyclerView rv = view.findViewById(R.id.rv);
        rv.setHasFixedSize(false);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        List<LectureAttendance> lectureAttendance;
        lectureAttendance = new ArrayList<>();

        lectureAttendance.add(new LectureAttendance(1, "September 4", 75));
        lectureAttendance.add(new LectureAttendance(2, "September 8", 0));
        lectureAttendance.add(new LectureAttendance(3, "September 12", 75));
        lectureAttendance.add(new LectureAttendance(4, "September 14", 75));
        ProfClassAttendanceRVAdapter adapter = new ProfClassAttendanceRVAdapter(lectureAttendance, courseCode);
        rv.setAdapter(adapter);

        return view;
    }
}
