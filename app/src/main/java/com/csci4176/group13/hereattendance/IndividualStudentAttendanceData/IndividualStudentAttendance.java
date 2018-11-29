package com.csci4176.group13.hereattendance.IndividualStudentAttendanceData;

public class IndividualStudentAttendance {

    private String studentName;
    private boolean attended;

    public IndividualStudentAttendance(String sn, boolean a) {
        studentName = sn;
        attended = a;
    }

    /* Get and set methods */
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public boolean isAttended() {
        return attended;
    }
    public void setAttended(boolean attended) {
        this.attended = attended;
    }
}
