[mockup]: https://github.com/jbstks/CSCI4176-HereAttendance/blob/master/app/src/main/HereAttendance-Mockup.png "Here! Attendance Mockup"

## Final Project: Here! Attendance

### Description
Attendance can be spotty in labs and lectures. Even when students are supposed to receive credit for their attendance, sometimes the attendance sheet will go missing, or students will sign it when they walked in very late. We have conceptualized a solution that will use QR codes and GPS to verify a student’s attendance. The QR code will be generated and provided to the professor before the class, and they can display it to the students in whatever manner they prefer. The date, time and location of the students will be recorded when they scan the QR code to ensure honest reporting, and the professor will have access to the attendance record.

### High-Level Organization
#### Professor View
![Here! Attendance Mockup][mockup]
1. The login screen of the app where the professor can enter their university ID and password. The sign in button will verify the professor entered valid credentials.
2. The attendance screen, where the professor can view the courses they instruct, and their overall attendance for each course.
3. The lecture screen displays the overall attendance of the students that attended the class and scanned the QR code using our application.
4. The drawer that shows the attendance history (list of courses the instructor instructs) and allows the professor to log out.
5. The QR that gets generated automatically by the application.
6. The lecture screen allows the professor to generate a new QR code for each lecture, also to see their previous attendance records for a specific lecture.
