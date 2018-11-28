package com.csci4176.group13.hereattendance.AttendanceData;

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
public class ClassAttendanceRVAdapter extends RecyclerView.Adapter<ClassAttendanceRVAdapter.CurrCourseViewHolder> {

    private final List<LectureAttendance> lectureAttendanceData;

    /**
     * Constructor to create a RecyclerView adapter
     *
     * @param ad the attendance data list
     */
    public ClassAttendanceRVAdapter(List<LectureAttendance> ad) {
        lectureAttendanceData = ad;
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
            lectureNum = (TextView) view.findViewById(R.id.lectureNum);
            date = (TextView) view.findViewById(R.id.date);
            isAttended = (TextView) view.findViewById(R.id.isAttended);
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
                .inflate(R.layout.class_attendance_list, parent, false);
        return new CurrCourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CurrCourseViewHolder holder, int position) {
        holder.lectureNum.setText("Lecture "+ lectureAttendanceData.get(position).getLectureNum());
        holder.date.setText(lectureAttendanceData.get(position).getDate());
        if (lectureAttendanceData.get(position).isAttended())
            holder.isAttended.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_round_done_24px, 0, 0, 0);
        else
            holder.isAttended.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_round_close_24px, 0, 0, 0);
    }

    @Override
    public int getItemCount() {
        return lectureAttendanceData.size();
    }
}
