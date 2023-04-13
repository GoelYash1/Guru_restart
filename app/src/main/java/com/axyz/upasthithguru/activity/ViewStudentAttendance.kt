package com.axyz.upasthithguru.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.Realm.ClassAttendance
import com.axyz.upasthithguru.Realm.ClassAttendanceManager
import com.axyz.upasthithguru.adapters.studentAttendanceListAdapter
import com.axyz.upasthithguru.databinding.ActivityViewStudentAttendanceBinding
import com.axyz.upasthithguru.fragments.selectedCoursePassed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
//import com.axyz.upasthithguru.adapters.AttendanceAdapter

// These Activity shows the dates of attendance record for the teacher
class ViewStudentAttendance : AppCompatActivity() {
    lateinit var binding: ActivityViewStudentAttendanceBinding
    private lateinit var rvAttendance: RecyclerView
    private lateinit var courseId: ObjectId
    private lateinit var fetchedStudentRecords: ClassAttendance

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityViewStudentAttendanceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var btn = binding.viewStudentAddStudents
        btn.setOnClickListener {
//            ClassAttendanceManager().addStudentRecord(StudentRecord("yashsahu@gmail.com", true, "success", Date()))
        //            finish()
//            CourseRepository().examTry()
        }
        var passedId = selectedCoursePassed?._id
        courseId = passedId?.let { ClassAttendanceManager().createAttendanceRecord(it) }!!
        // initialize fetchedStudentRecords to an empty ClassAttendance object
        Toast.makeText(this,"${courseId.toHexString()}",Toast.LENGTH_SHORT).show()
        fetchedStudentRecords = ClassAttendance()
        rvAttendance = findViewById(R.id.rvAttendance)
        rvAttendance.layoutManager = LinearLayoutManager(applicationContext)
        rvAttendance.adapter = studentAttendanceListAdapter(fetchedStudentRecords)

        CoroutineScope(Dispatchers.Main).launch {
            // fetch attendance records from the database
            Log.d(TAG,"Attendance Records --------------- > ${ClassAttendanceManager().getClassAttendanceRecord(courseId)}")
            val attendanceRecords = ClassAttendanceManager().getClassAttendanceRecord(courseId)
            if (attendanceRecords != null) {
                Log.d(TAG,"attendance Records is not null ----------------->")
                // update fetchedStudentRecords with the actual attendance records
                fetchedStudentRecords = attendanceRecords
                // update the RecyclerView adapter with the updated fetchedStudentRecords
                rvAttendance.adapter?.notifyDataSetChanged()
                Log.d(TAG,"Hello is the data changed---------->${rvAttendance.adapter?.notifyDataSetChanged()}")
            } else {
                // handle error
            }
        }

//        Log.d(TAG,"Fetched Course attendence ---- ${CourseRepository().getCourse(courseId)?.id}")
//        Log.d(TAG,"Fetched Course attendence ---- ${CourseRepository().getCourse(courseId)?.courseCredits}")
//        Log.d(TAG,"Fetched Course attendence ---- ${CourseRepository().getCourse(courseId)?.name}")
//        Log.d(TAG,"Fetched Course attendence ---- ${CourseRepository().getCourse(courseId)?.courseAttendance}")
//        Log.d(TAG,"Fetched Course ADDRESS ---- ${CourseRepository().getCourse(courseId)?.addresses}")

    }

}
