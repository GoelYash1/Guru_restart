package com.axyz.upasthithguru.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.Realm.CourseRepository
import java.util.*

// Shows the Attendance record for a specific Date
class ViewStudentAttendanceDate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_student_attendance_date)

        // the Date and the course id Passed below should be fetched from the previous activity
//        val fetchedDateRecord = CourseRepository().getAttendance("123", Date())

    }
}