package com.csci4176.group13.hereattendance.Fragments.Student;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csci4176.group13.hereattendance.R;

/**
 * Fragment to hold student's list values of class attendance
 */
public class StudentClassAttendanceFragment extends Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StudentClassAttendanceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_class_attendance, container, false);

        /*RecyclerView rv = view.findViewById(R.id.rv);
        rv.setHasFixedSize(false);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        List<CurrentCourse> currentCourses;
        currentCourses = new ArrayList<>();

        currentCourses.add(new CurrentCourse("CSCI3130", "Software Engineering", 60));
        currentCourses.add(new CurrentCourse("CSCI3130", "Software Engineering", 60));
        currentCourses.add(new CurrentCourse("CSCI4176", "Mobile Computing", 75));
        currentCourses.add(new CurrentCourse("CSCI3110", "Algorithms", 98));

        CurrCoursesRVAdapter adapter = new CurrCoursesRVAdapter(currentCourses);
        rv.setAdapter(adapter);*/

        return view;
    }
}
