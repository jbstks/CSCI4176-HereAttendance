package com.csci4176.group13.hereattendance.AttendanceData;

public class ClassAttendance {

    private int lectureNum;
    private String date;
    private boolean attended;

    /**
     * Constructor
     *
     * @param lectureNum the lecture number
     * @param date the date of the lecture
     * @param attended whether or not it was attended
     */
    public ClassAttendance(int lectureNum, String date, boolean attended) {
        this.lectureNum = lectureNum;
        this.date = date;
        this.attended = attended;
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
