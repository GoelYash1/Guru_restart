package com.axyz.upasthithguru.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.Realm.ClassAttendanceManager
import com.axyz.upasthithguru.Realm.EnrollStudentsManager
import com.axyz.upasthithguru.Realm.EnrolledStudent
import com.axyz.upasthithguru.app
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mongodb.kbson.BsonObjectId

class StudentsEnrolled : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_enrolled)
        val enrollStudents = findViewById<Button>(R.id.btn_enrollStudents)
        val passedId = intent.getByteArrayExtra("Course Id")?.let { BsonObjectId(it) }!!
//        courseId = passedId
        enrollStudents.setOnClickListener {
//            startActivity(Intent(this,EnrollStudentForm::class.java))
//            EnrollStudentsManager().enrollStudent(passedId,"y@gmail.com","Yash")
//            EnrollStudentsManager().getAllEnrolledStudent()
//            CoroutineScope()
            CoroutineScope(Dispatchers.Main).launch {
                ClassAttendanceManager().getAllStudentRecords()
            }
//            ClassAttendanceManager().getAllStudentRecords()
            Log.d("Enrolled Students ::","${app.currentUser}")

        }
    }
}