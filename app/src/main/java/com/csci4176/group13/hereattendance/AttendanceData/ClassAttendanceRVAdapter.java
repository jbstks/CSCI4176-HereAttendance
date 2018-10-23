package com.csci4176.group13.hereattendance.AttendanceData;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csci4176.group13.hereattendance.ClassAttendanceActivity;
import com.csci4176.group13.hereattendance.CurrentCourses.CurrentCourse;
import com.csci4176.group13.hereattendance.R;

import java.util.List;

/**
 * RecyclerView Adapter for user's current courses
 *
 * @author Joanna Bistekos
 */
public class ClassAttendanceRVAdapter extends RecyclerView.Adapter<ClassAttendanceRVAdapter.CurrCourseViewHolder> {

    private final List<AttendanceData> attendanceData;

    /**
     * Constructor to create a RecyclerView adapter
     *
     * @param ad the attendance data list
     */
    public ClassAttendanceRVAdapter(List<AttendanceData> ad) {
        attendanceData = ad;
    }

    /**
     * Allows us to select list values individually
     */
    public static class CurrCourseViewHolder extends RecyclerView.ViewHolder {

        public final LinearLayout listItem;
        public final TextView lectureNum;
        public final TextView date;
        public final TextView isAttended;

        CurrCourseViewHolder(View view) {
            super(view);

            listItem = view.findViewById(R.id.listItem);
            code = (TextView) view.findViewById(R.id.code);
            name = (TextView) view.findViewById(R.id.name);
            attendancePercentage = (TextView) view.findViewById(R.id.attendancePercent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), ClassAttendanceActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
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
                .inflate(R.layout.attendance_history_list, parent, false);
        return new CurrCourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CurrCourseViewHolder holder, int position) {
        holder.lectureNum.setText("Lecture "+attendanceData.get(position).getLectureNum());
        holder.date.setText(attendanceData.get(position).getDate());
        holder.attendancePercentage.setText(Integer.toString(currentCourses.get(position).getAttendancePercent()) + "%");
    }

    @Override
    public int getItemCount() {
        return currentCourses.size();
    }
}
