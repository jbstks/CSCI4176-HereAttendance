package com.csci4176.group13.hereattendance.IndividualStudentAttendanceData;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csci4176.group13.hereattendance.R;

import java.util.List;

/**
 * RecyclerView Adapter for a professor's courses
 *
 * @author Joanna Bistekos
 */
public class IndividualStudentAttendanceRVAdapter extends RecyclerView.Adapter<IndividualStudentAttendanceRVAdapter.IndividualAttendanceViewHolder> {

    private final List<IndividualStudentAttendance> studentAttendancesData;

    /**
     * Constructor to create a RecyclerView adapter
     *
     * @param sd the attendance data list
     */
    public IndividualStudentAttendanceRVAdapter(List<IndividualStudentAttendance> sd) {
        studentAttendancesData = sd;
    }

    /**
     * Allows us to select list values individually
     */
    public static class IndividualAttendanceViewHolder extends RecyclerView.ViewHolder {

        public final LinearLayout listItem;
        public final TextView studentName;
        public TextView isAttended;

        IndividualAttendanceViewHolder(View view) {
            super(view);

            listItem = view.findViewById(R.id.listItem);
            studentName = (TextView) view.findViewById(R.id.studentName);
            isAttended = (TextView) view.findViewById(R.id.isAttended);
        }
    }

    /**
     * Creates the list items
     *
     * @param parent
     * @param viewType
     */
    @Override
    public IndividualAttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_attendance_list, parent, false);
        return new IndividualAttendanceViewHolder(view);
    }

    /**
     * Setting the values of the list
     *
     * @param holder IndividualAttendanceViewHolder used
     * @param position position in the ArrayList
     */
    @Override
    public void onBindViewHolder(final IndividualAttendanceViewHolder holder, int position) {
        holder.studentName.setText(studentAttendancesData.get(position).getStudentName());
        if (studentAttendancesData.get(position).isAttended())
            holder.isAttended.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_round_done_24px, 0);
        else
            holder.isAttended.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_round_close_black_24px, 0);
    }

    /**
     * Returns the number of items in the list
     *
     * @return int the size of the studentAttendancesData list
     */
    @Override
    public int getItemCount() {
        return studentAttendancesData.size();
    }
}
