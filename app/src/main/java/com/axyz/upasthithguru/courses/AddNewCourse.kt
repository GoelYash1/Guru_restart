package com.axyz.upasthithguru.courses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.Realm.Course
import com.axyz.upasthithguru.Realm.CourseRepository

class AddNewCourse : AppCompatActivity() {
//    var CourseRepository = CourseRepository()
    private lateinit var etName: EditText
    private lateinit var etCode: EditText
    private lateinit var etCredits: EditText
    private lateinit var spDepartment: Spinner
    private lateinit var etDescription: EditText
    private lateinit var etYear: EditText
    private lateinit var etSemester: EditText
    private lateinit var btnSave: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_course)

        etName = findViewById(R.id.et_name)
        etCode = findViewById(R.id.et_code)
        etCredits = findViewById(R.id.et_credit)
        spDepartment = findViewById(R.id.sp_department)
        etDescription = findViewById(R.id.et_description)
        etYear = findViewById(R.id.et_year)
        etSemester = findViewById(R.id.et_semester)
        btnSave = findViewById(R.id.createCourseButton)


        etName.setText("Android")
        etCode.setText("CS-101")
        etCredits.setText("3")
        etDescription.setText("Android Development")
        etYear.setText("2021")
        etSemester.setText("1")


        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val code = etCode.text.toString().trim()
            val credits = etCredits.text.toString().trim()
            val department = spDepartment.selectedItem.toString().trim()
            val year = etYear.text.toString().trim()
            val semester = etSemester.text.toString().trim()
            val description = etDescription.text.toString().trim()

            // Perform any necessary validations here
            if (name.isEmpty()) {
                etName.error = "Name is required"
                etName.requestFocus()
                return@setOnClickListener
            }

            if (code.isEmpty()) {
                etCode.error = "Code is required"
                etCode.requestFocus()
                return@setOnClickListener
            }

            if (credits.isEmpty()) {
                etCredits.error = "Credits are required"
                etCredits.requestFocus()
                return@setOnClickListener
            }

            if (year.isEmpty()) {
                etYear.error = "Year is required"
                etYear.requestFocus()
                return@setOnClickListener
            }

            if (semester.isEmpty()) {
                etSemester.error = "Semester is required"
                etSemester.requestFocus()
                return@setOnClickListener
            }

            // Save the course data to the database or perform any necessary actions
//            CourseRepository.insertCourse(Course("1", name,code,description,credits,department,year,semester,"lkjhlkjh"))
//            CourseRepository.insertCourse(Course(name,code))
            Toast.makeText(this, "Course Added.....",Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}