package com.csci4176.group13.hereattendance.IndividualStudentAttendanceData;

public class IndividualStudentAttendance {

    private String studentName;
    private boolean attended;

    /**
     * Constructor
     *
     * @param studentName name of student
     * @param attended whether or not they attended the course
     */
    public IndividualStudentAttendance(String studentName, boolean attended) {
        this.studentName = studentName;
        this.attended = attended;
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
