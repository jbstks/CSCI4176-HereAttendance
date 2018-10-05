package com.csci4176.group13.hereattendance.currentcourses;

/**
 * Data structure to hold course data
 */
public class CurrentCourse {

    private String code;
    private String name;
    private int attendancePercent;

    /**
     * Default constructor
     */
    public CurrentCourse() {}

    /**
     * Constructor to create a CurrentCourse object with the following data
     * @param c     the course code
     * @param n     the course name
     * @param ap    the percentage of one's attendance in this course
     */
    public CurrentCourse(String c, String n, int ap) {
        code = c;
        name = n;
        attendancePercent = ap;
    }

    /**
     *  Get and set methods for code, name and attendance percent
     */
    public String getCode() { return code; }
    public void setCode(String c) { code = c; }

    public String getName() { return name; }
    public void setName(String n) { name = n; }

    public int getAttendancePercent() { return attendancePercent; }
    public void setAttendancePercent(int ap) { attendancePercent = ap; }
}
