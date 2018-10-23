package com.csci4176.group13.hereattendance.AttendanceData;

public class AttendanceData {

    private int lectureNum;
    private String date;
    private boolean attended;

    public AttendanceData(int l, String d, boolean a) {
        lectureNum = l;
        date = d;
        attended = a;
    }

    /* Get and set methods */

    public int getLectureNum() {
        return lectureNum;
    }
    public void setLectureNum(int lectureNum) {
        this.lectureNum = lectureNum;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public boolean isAttended() {
        return attended;
    }
    public void setAttended(boolean attended) {
        this.attended = attended;
    }
}
