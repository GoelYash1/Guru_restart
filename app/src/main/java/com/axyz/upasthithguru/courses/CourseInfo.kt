package com.axyz.upasthithguru.courses

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.axyz.upasthithg.Realm.StudentRecord
import com.axyz.upasthithguru.Realm.Course
import com.axyz.upasthithguru.Realm.CourseRepository
import com.axyz.upasthithguru.activity.StudentsEnrolledActivity
import com.axyz.upasthithguru.activity.ViewStudentAttendance
import com.axyz.upasthithguru.data.realmModule.realm
import com.axyz.upasthithguru.databinding.ActivityCourseInfoBinding
import io.realm.kotlin.ext.query
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId
import java.text.SimpleDateFormat
import java.util.*

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
        attendanceCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Construct the date string from the selected year, month, and day
            val dateString = "$year-${month + 1}-$dayOfMonth"

            // Format the date string using SimpleDateFormat
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDateString = dateFormatter.format(dateFormatter.parse(dateString))

            // Create an Intent to launch the new activity
            val intent = Intent(this, ViewStudentAttendance::class.java)

            // Add the selected date as an extra to the Intent
            intent.putExtra("Attendance Date", formattedDateString)
            intent.putExtra("Student Course Attendance Id", passedId.toByteArray())

            // Start the new activity
            startActivity(intent)
        }


        enrolledStudents.setOnClickListener {
            val intent = Intent(this,StudentsEnrolledActivity::class.java)
            intent.putExtra("Course Id",courseId.toByteArray())
            startActivity(intent)
        }

        val c = CourseRepository().getCourse(courseId)
        val records = realm.query<StudentRecord>().find()
        Log.d("Course Records ::","${records}")
        Log.d("Course ::","${c?.name}")
        Log.d("Course ::","${c?.totalNoOfClasses}")
//        CoroutineScope(Dispatchers.Main).launch {
//            ClassAttendanceManager().addStudentRecord(
//                passedId,
//                1,
//                "yst@gmail.com",
//            )
//        }
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