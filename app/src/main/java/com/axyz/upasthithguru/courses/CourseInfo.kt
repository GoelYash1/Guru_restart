package com.axyz.upasthithguru.courses

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.axyz.upasthithguru.Realm.Course
import com.axyz.upasthithguru.Realm.CourseRepository
import com.axyz.upasthithguru.activity.StudentsEnrolled
import com.axyz.upasthithguru.databinding.ActivityCourseInfoBinding
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class CourseInfo : AppCompatActivity() {
    private lateinit var courseId:ObjectId
    private lateinit var editCourseInformation : CardView
    private lateinit var enrolledStudents : CardView
    private lateinit var credits : TextView
    private lateinit var department : TextView
    private lateinit var semester : TextView
    private lateinit var courseCode: TextView
    private lateinit var courseName: TextView
    private lateinit var description: TextView
    private lateinit var attendanceCalendar: CalendarView
    private lateinit var binding: ActivityCourseInfoBinding
    private lateinit var course: Course
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCourseInfoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Get the Course Id
        val passedId = intent.getByteArrayExtra("Course Id")?.let { BsonObjectId(it) }!!
        courseId = passedId

        // initialize all the View in activity
        courseCode = binding.coursecode
        courseName = binding.coursename
        credits = binding.textCredits
        department = binding.textDepartment
        semester = binding.textSem
        description = binding.courseDescription

        // Card Views acting as button
        enrolledStudents = binding.enrolledStudents
        editCourseInformation = binding.editCourse

        // Calendar View
        attendanceCalendar = binding.courseInfoAttendanceCalendar
        enrolledStudents.setOnClickListener {
            val intent = Intent(this,StudentsEnrolled::class.java)
            intent.putExtra("Course Id",courseId.toByteArray())
            startActivity(intent)
        }
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