package com.csci4176.group13.hereattendance.AttendanceData;

public class LectureAttendance {

    private int lectureNum;
    private String date;
    private boolean attended;
    private int attendancePercentage;

    // Student
    public LectureAttendance(int l, String d, boolean a) {
        lectureNum = l;
        date = d;
        attended = a;
    }

    // Professor
    public LectureAttendance(int l, String d, int ap) {
        lectureNum = l;
        date = d;
        attendancePercentage = ap;
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

    public int getAttendancePercentage() { return attendancePercentage; }
    public void setAttendancePercentage(int attendancePercentage) {this.attendancePercentage = attendancePercentage; }
}
