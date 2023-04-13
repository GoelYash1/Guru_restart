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
import com.axyz.upasthithguru.Realm.StudentRecord
import com.axyz.upasthithguru.adapters.studentAttendanceListAdapter
import com.axyz.upasthithguru.databinding.ActivityViewStudentAttendanceBinding
import com.axyz.upasthithguru.fragments.selectedCoursePassed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mongodb.kbson.ObjectId
//import com.axyz.upasthithguru.adapters.AttendanceAdapter

// These Activity shows the dates of attendance record for the teacher
class ViewStudentAttendance : AppCompatActivity() {
    private lateinit var binding: ActivityViewStudentAttendanceBinding
    private lateinit var rvAttendance: RecyclerView
    private lateinit var courseId: ObjectId
    private lateinit var studentAttendanceListAdapter: studentAttendanceListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewStudentAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        courseId = intent.getByteArrayExtra("Student Course Attendance Id")?.let { ObjectId(it) }!!

        setupRecyclerView()
        loadAttendanceData()
        setupAddAttendanceButton()
    }

    private fun setupRecyclerView() {
        rvAttendance = binding.rvAttendance
        rvAttendance.layoutManager = LinearLayoutManager(this)
        rvAttendance.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        studentAttendanceListAdapter = studentAttendanceListAdapter(ClassAttendance())
        rvAttendance.adapter = studentAttendanceListAdapter
    }

    private fun loadAttendanceData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val attendanceRecords = ClassAttendanceManager().getClassAttendanceRecord(courseId)
                attendanceRecords?.let {
                    withContext(Dispatchers.Main) {
                        rvAttendance.adapter = studentAttendanceListAdapter(attendanceRecords)
                        studentAttendanceListAdapter.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading attendance data: $e")
            }
        }
    }

    private fun setupAddAttendanceButton() {
        binding.viewStudentAddStudents.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    ClassAttendanceManager().addStudentRecord(courseId, "yashsahu@gmail.com", "success")
                    loadAttendanceData()
                } catch (e: Exception) {
                    Log.e(TAG, "Error adding attendance record: $e")
                }
            }
        }
    }
}
