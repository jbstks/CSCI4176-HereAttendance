package com.csci4176.group13.hereattendance.CurrentCourses;

/**
 * Data structure to hold course data
 */
public class CurrentCourse {

    private String code;
    private String name;
    private int attendancePercent;

    /**
     * Constructor to create a CurrentCourse object with the following data
     * @param code              the course code
     * @param name              the course name
     * @param attendancePercent the percentage of one's/classes attendance in this course (depends on student/prof)
     */
    public CurrentCourse(String code, String name, int attendancePercent) {
        this.code = code;
        this.name = name;
        this.attendancePercent = attendancePercent;
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
