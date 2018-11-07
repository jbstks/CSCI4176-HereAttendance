package com.csci4176.group13.hereattendance.AttendanceData;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csci4176.group13.hereattendance.ClassAttendanceActivity;
import com.csci4176.group13.hereattendance.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        FirebaseUser signedInUser = FirebaseAuth.getInstance().getCurrentUser();
        String user;

        public final LinearLayout listItem;
        public final TextView lectureNum;
        public final TextView date;
        public final TextView isAttended;

        CurrCourseViewHolder(View view) {
            super(view);

            if (signedInUser != null)
                user = signedInUser.getEmail();

            listItem = view.findViewById(R.id.listItem);
            lectureNum = (TextView) view.findViewById(R.id.lectureNum);
            date = (TextView) view.findViewById(R.id.date);
            isAttended = (TextView) view.findViewById(R.id.isAttended);
            if ( user.equals("professor@here.com") ) {
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
