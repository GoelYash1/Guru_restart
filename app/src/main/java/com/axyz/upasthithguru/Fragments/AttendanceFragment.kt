package com.axyz.upasthithguru.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.Realm.Course
import com.axyz.upasthithguru.Realm.CourseRepository
import com.axyz.upasthithguru.activity.Profile
import com.axyz.upasthithguru.activity.TakeAttendance
import io.realm.kotlin.query.RealmResults
import org.mongodb.kbson.ObjectId

var selectedCoursePassed :Course? = null
class Attendance : Fragment() {
    private lateinit var courseDropDownButton: AutoCompleteTextView
    private val courseList: MutableList<Pair<ObjectId, String>> = mutableListOf()
    private lateinit var fetchedCourse : RealmResults<Course>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        courseDropDownButton = view.findViewById(R.id.courseDropDownView)
        fetchedCourse = CourseRepository().getAllCourse()
        updateDropDownAdapter()

    }

    override fun onResume() {
        super.onResume()
        updateDropDownAdapter()
    }

    private fun updateDropDownAdapter() {
        courseList.clear()
        fetchedCourse = CourseRepository().getAllCourse()
        for (course in fetchedCourse){
            courseList.add(course._id to course.name)
        }
        val courseNames = courseList.map { it.second }
        courseDropDownButton.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.course_list_dropdown,
                courseNames
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_attendance, container, false)
        view.findViewById<ImageView>(R.id.attendanceFragmentProfile).setOnClickListener{
            startActivity(Intent(requireContext(),Profile::class.java))
        }
        view.findViewById<CardView>(R.id.attendanceFragmentStartAttendance).setOnClickListener {
            val selectedCourse = courseDropDownButton.text.toString().trim()
            Log.d("adsf","$selectedCourse")
            if (selectedCourse.isNotEmpty()) {
                val courseId = courseList.find { it.second == selectedCourse}?.first
                val intent = Intent(requireContext(), TakeAttendance::class.java)
                selectedCoursePassed = fetchedCourse.find { it._id == courseId }
                intent.putExtra("Course Id", selectedCoursePassed?._id?.toByteArray())
                startActivity(intent)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please select a course name from the dropdown",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return view
    }
}

