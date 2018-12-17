package com.csci4176.group13.hereattendance.Fragments.Professor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csci4176.group13.hereattendance.CurrentCourses.CurrCoursesRVAdapter;
import com.csci4176.group13.hereattendance.CurrentCourses.CurrentCourse;
import com.csci4176.group13.hereattendance.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment to hold list values of current courses
 */
public class ProfAttendanceHistoryFragment extends Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProfAttendanceHistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_history, container, false);

        // Setting up the RecyclerView (list)
        RecyclerView rv = view.findViewById(R.id.rv);
        rv.setHasFixedSize(false);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        List<CurrentCourse> currentCourses;
        currentCourses = new ArrayList<>();

        // these magic numbers are my best estimate at course attendance.
        //currentCourses.add(new CurrentCourse("CSCI3130", "Software Engineering", 9 * 100 / 14));
        currentCourses.add(new CurrentCourse("CSCI3130", "Software Engineering", 9 * 100 / 14));
        currentCourses.add(new CurrentCourse("CSCI4176", "Mobile Computing", 8 * 100 / 14));
        currentCourses.add(new CurrentCourse("CSCI3110", "Algorithms", 8 * 100 / 12));

        CurrCoursesRVAdapter adapter = new CurrCoursesRVAdapter(currentCourses);
        rv.setAdapter(adapter);

        return view;
    }
}
