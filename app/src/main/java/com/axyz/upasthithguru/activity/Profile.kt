package com.axyz.upasthithguru.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.Realm.CourseRepository
import com.axyz.upasthithguru.adapters.CourseListAdapter
import com.axyz.upasthithguru.databinding.ActivityProfileBinding

class Profile : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    lateinit var coursesProfile:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fetchedCourses = CourseRepository().getAllCourse()
        coursesProfile = binding.profileRecView
        val courseListAdapter = CourseListAdapter(fetchedCourses)
        coursesProfile.layoutManager = LinearLayoutManager(applicationContext)
        coursesProfile.adapter = courseListAdapter
    }
}