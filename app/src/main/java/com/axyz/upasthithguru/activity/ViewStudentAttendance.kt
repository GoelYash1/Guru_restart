package com.axyz.upasthithguru.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.Realm.ClassAttendance
import com.axyz.upasthithguru.Realm.ClassAttendanceManager
import com.axyz.upasthithguru.Realm.CourseRepository
import com.axyz.upasthithguru.Realm.StudentRecord
//import com.axyz.upasthithguru.adapters.AttendanceAdapter
import java.util.*

// These Activity shows the dates of attendance record for the teacher
class ViewStudentAttendance : AppCompatActivity() {

    private lateinit var rvAttendance: RecyclerView
    private lateinit var courseId: String
    private lateinit var fetchedStudentRecords: MutableList<ClassAttendance>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_student_attendance)

        var btn = findViewById<Button>(R.id.button3)
        btn.setOnClickListener {
//            ClassAttendanceManager().addStudentRecord(StudentRecord("yashsahu@gmail.com", true, "success", Date()))
        //            finish()
//            CourseRepository().examTry()
        }

        courseId = intent.getStringExtra("Course Id").toString()
//        fetchedStudentRecords = CourseRepository().getAllAttendance(courseId) ?: mutableListOf()
//        Log.d(TAG,"Fetched REcords $courseId---- $fetchedStudentRecords")
//        Log.d(TAG,"Fetched Course attendence ---- ${CourseRepository().getCourse(courseId)?.id}")
//        Log.d(TAG,"Fetched Course attendence ---- ${CourseRepository().getCourse(courseId)?.courseCredits}")
//        Log.d(TAG,"Fetched Course attendence ---- ${CourseRepository().getCourse(courseId)?.name}")
//        Log.d(TAG,"Fetched Course attendence ---- ${CourseRepository().getCourse(courseId)?.courseAttendance}")
//        Log.d(TAG,"Fetched Course ADDRESS ---- ${CourseRepository().getCourse(courseId)?.addresses}")

        Toast.makeText(this,"Hello View Attendance",Toast.LENGTH_SHORT).show()
        rvAttendance = findViewById(R.id.rvAttendance)
        rvAttendance.layoutManager = LinearLayoutManager(this)
        rvAttendance.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
//        rvAttendance.adapter = AttendanceAdapter(fetchedStudentRecords)
    }

}
