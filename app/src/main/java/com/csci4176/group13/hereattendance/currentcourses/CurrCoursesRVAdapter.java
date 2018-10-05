package com.csci4176.group13.hereattendance.currentcourses;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csci4176.group13.hereattendance.R;

import java.util.List;

/**
 * RecyclerView Adapter for user's current courses
 *
 * @author Joanna Bistekos
 */
public class CurrCoursesRVAdapter extends RecyclerView.Adapter<CurrCoursesRVAdapter.CurrCourseViewHolder> {

    private final List<CurrentCourse> currentCourses;

    /**
     * Constructor to create a RecyclerView adapter
     *
     * @param cc the current course list
     */
    public CurrCoursesRVAdapter(List<CurrentCourse> cc) {
        currentCourses = cc;
    }

    /**
     * Allows us to select list values individually
     */
    public static class CurrCourseViewHolder extends RecyclerView.ViewHolder {

        public final LinearLayout listItem;
        public final TextView code;
        public final TextView name;
        public final TextView attendancePercentage;

        CurrCourseViewHolder(View view) {
            super(view);

            listItem = view.findViewById(R.id.listItem);
            code = (TextView) view.findViewById(R.id.code);
            name = (TextView) view.findViewById(R.id.name);
            attendancePercentage = (TextView) view.findViewById(R.id.attendancePercent);
            // set on click here
        }
    }

    /**
     * Constructor
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public CurrCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_currcourses_list, parent, false);
        return new CurrCourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CurrCourseViewHolder holder, int position) {
        holder.code.setText(currentCourses.get(position).getCode());
        holder.name.setText(currentCourses.get(position).getName());
        holder.attendancePercentage.setText(Integer.toString(currentCourses.get(position).getAttendancePercent()) + "%");
    }

    @Override
    public int getItemCount() {
        return currentCourses.size();
    }
}
