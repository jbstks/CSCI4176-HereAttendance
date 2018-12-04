package com.csci4176.group13.hereattendance.AttendanceData;

/**
 * Data structure to hold the lecture attendance
 */
public class LectureAttendance {

    private int lectureNum;
    private String date;
    private boolean attended;
    private int attendancePercentage;

    /**
     * Student
     * Constructor
     *
     * @param lectureNum the lecture number
     * @param date the date of which the lecture took place
     * @param attended whether or not the lecture was attended
     */
    public LectureAttendance(int lectureNum, String date, boolean attended) {
        this.lectureNum = lectureNum;
        this.date = date;
        this.attended = attended;
    }

    /**
     * Professor
     * Constructor
     *
     * @param lectureNum the lecture number
     * @param date the date of which the lecture took place
     * @param attendancePercentage the percentage of attendance
     */
    public LectureAttendance(int lectureNum, String date, int attendancePercentage) {
        this.lectureNum = lectureNum;
        this.date = date;
        this.attendancePercentage = attendancePercentage;
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
