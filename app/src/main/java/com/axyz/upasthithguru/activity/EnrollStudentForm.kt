package com.axyz.upasthithguru.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.Realm.EnrollStudentsManager

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