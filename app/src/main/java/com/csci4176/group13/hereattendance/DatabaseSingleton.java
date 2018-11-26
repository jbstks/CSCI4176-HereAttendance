package com.csci4176.group13.hereattendance;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.csci4176.group13.hereattendance.AttendanceData.LectureAttendance;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSingleton {
   static FirebaseDatabase database = FirebaseDatabase.getInstance();
   static DatabaseReference myRef= database.getReference();
    static List<LectureAttendance> studentAttendance2 = new ArrayList<>();
    static boolean pulled=false;

public static DatabaseReference getRef(){
    return myRef;
}

public static  getStudentLectureAttendance(String courseID,List<LectureAttendance> studentAttendance) {
    DatabaseReference myRef2 = myRef.child(courseID);
    myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            // emptying it again
            //List<LectureAttendance> studentAttendance = new ArrayList<>();

            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String lectureNum = snapshot.getKey();
                String lectureDate = snapshot.child("date").getValue().toString();
                Boolean attend = snapshot.hasChild("student");
                studentAttendance.add(new LectureAttendance(Integer.parseInt(lectureNum), lectureDate, attend));
            }
           pulled=true;
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    });
    if(pulled==true)
        return  studentAttendance;

}
}
