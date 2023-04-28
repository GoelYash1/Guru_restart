package com.axyz.upasthithguru.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.axyz.upasthithguru.R

class EnrollStudentForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new2_enroll_student)
        var enrollBtn = findViewById<Button>(R.id.btn_enroll).setOnClickListener {
            Log.d("hello ::", "Enrol Clicked")
//            EnrollStudentsManager().enrollStudent()
        }
    }
}