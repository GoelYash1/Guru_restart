package com.axyz.upasthithguru.courses

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.Realm.Course
import com.axyz.upasthithguru.Realm.CourseRepository
import com.axyz.upasthithguru.activity.StudentsEnrolled
import com.axyz.upasthithguru.activity.ViewStudentAttendance

class CourseInfo : AppCompatActivity() {
    private lateinit var courseId:String
    private lateinit var courseName : TextView
    private lateinit var courseHeading : TextView
    private lateinit var courseCode : TextView
    private lateinit var department : TextView
    private lateinit var semester : TextView
    private lateinit var moreOnStudents :LinearLayout
    private lateinit var studentAttendanceBtn :LinearLayout
    private lateinit var editCourseInformation : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_info)
        // intialize the code
        courseId = intent.getStringExtra("Course Id").toString().trim()
////        val course = CourseRepository().getCourse(courseId)
//        Log.d(TAG,"course -- > $course")
//        Toast.makeText(this,"Course Id,${course?.id}",Toast.LENGTH_SHORT).show()
        // initialize all the View in activity
        courseHeading = findViewById(R.id.courseHeadingTextView)
        courseName = findViewById(R.id.courseNameTextView)
        courseCode = findViewById(R.id.courseCodeTextView)
        department = findViewById(R.id.departmentTextView)
        semester = findViewById(R.id.semesterTextView)

        // Linear layout and buttons acting as button
        moreOnStudents = findViewById(R.id.moreOnStudentsView)
        studentAttendanceBtn = findViewById(R.id.studentAttendenceLinearLayout)
        editCourseInformation = findViewById(R.id.editCourseButton)

        // Setting their text values
//        if (course!=null)
//        {
//            courseName.text = course.name
//            courseHeading.text = course.name
//            courseCode.text = course.courseCode
//            department.text = course.courseDepartment
//            semester.text = course.courseSemester
//
//            moreOnStudents.setOnClickListener{
//                startActivity(Intent(this, StudentsEnrolled::class.java))
//            }
//            studentAttendanceBtn.setOnClickListener{
//                val intent = Intent(this,ViewStudentAttendance::class.java)
//                intent.putExtra("Course Id",courseId.toString())
//                startActivity(intent)
//
//            }
//            editCourseInformation.setOnClickListener {
//                startActivity(Intent(this, EditCourse::class.java))
//            }
//        }

    }
}