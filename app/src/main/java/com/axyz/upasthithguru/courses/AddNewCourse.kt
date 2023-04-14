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
//    private lateinit var spDepartment: Spinner
    private lateinit var etDescription: EditText
    private lateinit var etYear: EditText
    private lateinit var etSemester: EditText
    private lateinit var btnSave: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_course)

        etName = findViewById(R.id.courseName)
        etCode = findViewById(R.id.courseCode)
        etCredits = findViewById(R.id.courseCredits)
//        spDepartment = findViewById(R.id.sp_department)
        etDescription = findViewById(R.id.courseDescription)
        etYear = findViewById(R.id.courseYear)
        etSemester = findViewById(R.id.courseSemester)
        btnSave = findViewById(R.id.btn_add)


        etName.setText("SDN")
        etCode.setText("CS-103")
        etCredits.setText("2")
        etDescription.setText("Software Defined Networking")
        etYear.setText("2022")
        etSemester.setText("2")


        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val code = etCode.text.toString().trim()
            val credits = etCredits.text.toString().trim()
//            val department = spDepartment.selectedItem.toString().trim()
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
            CourseRepository().insertCourse(Course(name,code))
            Toast.makeText(this, "Course Added.....",Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}