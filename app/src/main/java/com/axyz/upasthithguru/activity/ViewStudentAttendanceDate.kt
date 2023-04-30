package com.axyz.upasthithguru.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.axyz.upasthithguru.R

// Shows the Attendance record for a specific Date
class ViewStudentAttendanceDate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_student_attendance_date)

        // the Date and the course id Passed below should be fetched from the previous activity
//        val fetchedDateRecord = CourseRepository().getAttendance("123", Date())

    }
}