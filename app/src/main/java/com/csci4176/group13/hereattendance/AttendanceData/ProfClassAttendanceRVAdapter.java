package com.csci4176.group13.hereattendance.AttendanceData;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csci4176.group13.hereattendance.ProfIndividualAttendanceActivity;
import com.csci4176.group13.hereattendance.QRGeneratorActivity;
import com.csci4176.group13.hereattendance.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * RecyclerView Adapter for a professor's courses
 *
 * @author Joanna Bistekos
 */
public class ProfClassAttendanceRVAdapter extends RecyclerView.Adapter<ProfClassAttendanceRVAdapter.ProfClassAttendanceViewHolder> {

    private final List<LectureAttendance> lectureAttendanceData;
    private static String courseCode;

    /**
     * Constructor to create a RecyclerView adapter
     *
     * @param ad the attendance data list
     * @param cc the course code
     */
    public ProfClassAttendanceRVAdapter(List<LectureAttendance> ad, String cc) {
        lectureAttendanceData = ad;
        courseCode = cc;
    }

    /**
     * Allows us to select list values individually
     */
    public static class ProfClassAttendanceViewHolder extends RecyclerView.ViewHolder {

        public final LinearLayout listItem;
        public final TextView lectureNum;
        public final TextView date;
        public TextView isAttended;
        public TextView overallAttendancePercent;
        public CardView qrGenBtn;

        ProfClassAttendanceViewHolder(View view) {
            super(view);

            listItem = view.findViewById(R.id.listItem);
            lectureNum = (TextView) view.findViewById(R.id.lectureNum);
            overallAttendancePercent = (TextView) view.findViewById(R.id.overallAttendancePercent);

            // this is an approximate number for what the total course attendance would be
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

            date = (TextView) view.findViewById(R.id.date);
            qrGenBtn = (CardView) view.findViewById(R.id.qrGenBtn);

            qrGenBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), QRGeneratorActivity.class);
                    intent.putExtra("courseCode", courseCode);
                    intent.putExtra("date", date.getText());
                    intent.putExtra("lectureNum", lectureNum.getText().toString().substring(8));
                    ((Activity) v.getContext()).startActivityForResult(intent, 1);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ProfIndividualAttendanceActivity.class);
                    intent.putExtra("lectureNum", lectureNum.getText());
                    // we have to pass along the course code, because we have different students for different courses
                    // so we have to identify what course it is
                    intent.putExtra("courseCode", courseCode);
                    ((Activity) v.getContext()).startActivityForResult(intent, 1);
                }
            });
        }
    }

    /**
     * ProfClassAttendanceViewHolder constructor
     *
     * @param parent
     * @param viewType
     */
    @Override
    public ProfClassAttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prof_class_attendance_list, parent, false);
        return new ProfClassAttendanceViewHolder(view);
    }

    /**
     * Setting the values of the list
     *
     * @param holder   ProfClassAttendanceViewHolder used
     * @param position position in the ArrayList
     */
    @Override
    public void onBindViewHolder(final ProfClassAttendanceViewHolder holder, int position) {
        holder.lectureNum.setText("Lecture " + lectureAttendanceData.get(position).getLectureNum());
        holder.date.setText(lectureAttendanceData.get(position).getDate());
        if (lectureAttendanceData.get(position).getAttendancePercentage() != 0) {
            holder.overallAttendancePercent.setText(Integer.toString(lectureAttendanceData.get(position).getAttendancePercentage()) + "%");
            holder.overallAttendancePercent.setVisibility(View.VISIBLE);
            holder.qrGenBtn.setVisibility(View.GONE);
        } else {
            holder.overallAttendancePercent.setVisibility(View.GONE);
            holder.qrGenBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Returns the number of items in the list
     *
     * @return int the size of the lectureAttendanceData list
     */
    @Override
    public int getItemCount() {
        return lectureAttendanceData.size();
    }
}
