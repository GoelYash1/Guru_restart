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
import androidx.cardview.widget.CardView
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.Realm.Course
import com.axyz.upasthithguru.Realm.CourseRepository
import com.axyz.upasthithguru.activity.StudentsEnrolled
import com.axyz.upasthithguru.activity.ViewStudentAttendance

class CourseInfo : AppCompatActivity() {
    private lateinit var courseId:TextView
    private lateinit var courseName : TextView
//    private lateinit var courseHeading : TextView
    private lateinit var courseCode : TextView
    private lateinit var department : TextView
    private lateinit var credits : TextView
    private lateinit var semester : TextView
    private lateinit var courseDesc : TextView
    private lateinit var studentsEnrolled :LinearLayout
    private lateinit var addNewStudent : CardView
    private lateinit var strengthOfStudent : TextView
//    private lateinit var studentAttendanceBtn :LinearLayout
    private lateinit var editCourseInformation : CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_info)
        // intialize the code
////        val course = CourseRepository().getCourse(courseId)
//        Log.d(TAG,"course -- > $course")
//        Toast.makeText(this,"Course Id,${course?.id}",Toast.LENGTH_SHORT).show()
        // initialize all the View in activity
        courseName = findViewById(R.id.coursename)
        courseCode = findViewById(R.id.coursecode)
        department = findViewById(R.id.department_name)
        credits =findViewById(R.id.credits_no)
        semester=findViewById(R.id.sem_no)
        editCourseInformation=findViewById(R.id.edit_course)
        addNewStudent = findViewById(R.id.addNew)
        strengthOfStudent = findViewById(R.id.number_of_student)


//        semester = findViewById(R.id.courseDescDetail.and(R.id.sem_no))

        // Linear layout and buttons acting as button
//        studentsEnrolled = findViewById(R.id.studentsincourse)
//        editCourseInformation = findViewById(R.id.edit_course)

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