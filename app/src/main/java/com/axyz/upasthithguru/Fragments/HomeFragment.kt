package com.axyz.upasthithguru.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.Realm.ClassAttendanceManager
import com.axyz.upasthithguru.Realm.Course
import com.axyz.upasthithguru.activity.Profile
import com.axyz.upasthithguru.Realm.CourseRepository
import com.axyz.upasthithguru.adapters.CourseListAdapter
import com.axyz.upasthithguru.app
import com.axyz.upasthithguru.courses.AddNewCourse
import com.axyz.upasthithguru.courses.CourseInfo
//import com.axyz.upasthithguru.data.RealmSyncRepository
import com.axyz.upasthithguru.data.realmModule
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.mongodb.User
import io.realm.kotlin.mongodb.subscriptions
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.*
import javax.inject.Inject



class Home : Fragment() {
//    @Inject
//    lateinit var courseRepository: CourseRepository
    var courseList : RealmList<Course> = realmListOf<Course>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.homeRecView)

//        var course = CourseRepository();
//            var realmSyncRepository = RealmSyncRepository(onSyncError = { session, error ->
//                // error handling code
//                Log.d("Error Realm ONSYNC --- ","NOOOOOOOO   $error")
//            })

//        realmSyncRepository.isReady.observe(viewLifecycleOwner, Observer { isReady ->
//            if (isReady) {
//                // The Realm instance is ready to be used
//                Log.d("Realm ::","Ready ")
//            } else {
//                Log.d("Realm ::","NOT Ready -X-X-X-X-X-X-X-X-X-X-X-X-X-")
//                // The Realm instance is not
//            }
//        })

//        CoroutineScope(Dispatchers.Main).launch {
//
//        }
//        CoroutineScope(Dispatchers.Main).launch {
//            var realmSyncRepository: RealmSyncRepository? = null
//            while (realmSyncRepository == null) {
//                try {
//                    realmSyncRepository = RealmSyncRepository(onSyncError = { session, error ->
//                        // error handling code
//                        Log.d("Error Realm ONSYNC --- ","NOOOOOOOO   $error")
//                    })
////                    val liveData = realmSyncRepository.realmLiveData
//
////                    realmSyncRepository.isInitialized.observe(viewLifecycleOwner) { isInitialized ->
////                        if (isInitialized) {
////                            // Realm is initialized, do something
////                            Log.d("ISINIITIALIZEDD ----  ::: ","HO j bhai yr -> ${realmSyncRepository.realm.subscriptions.state}")
//////                            val courses = CourseRepository().getAllCourse()
////                            Log.d("Course ::: ","Course list hai bhai ----> $courses")
////                        } else {
////                            // Realm is not initialized yet
////                        }
////                    }
//
//                } catch (e: Exception) {
//                    Log.d("Error Realm Instanciating --- ","NOOOOOOOO   $e")
//                    delay(200)
//                }
//            }
//        }

        val courseListAdapter = CourseListAdapter(courseList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = courseListAdapter

        courseListAdapter.setOnItemClickListener(object : CourseListAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val id = courseList[position]._id.toString()
                val intent = Intent(this@Home.requireContext(), CourseInfo::class.java)
                intent.putExtra("Course Id",id)
                Toast.makeText(requireContext(),"Hello$id", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
        })

        view.findViewById<ImageView>(R.id.settingProfileImageView).setOnClickListener{
            val intent = Intent(this@Home.requireContext(), Profile::class.java)
            startActivity(intent)
        }
        view.findViewById<CardView>(R.id.addNewCourseCard).setOnClickListener {
            val intent = Intent(this@Home.requireContext(), AddNewCourse::class.java)
            startActivity(intent)
//            var classAttendanceId = ClassAttendanceManager().createAttendanceRecord(courseList.first()._id)
//            CoroutineScope(Dispatchers.Main).launch {
//                ClassAttendanceManager().addStudentRecord(
//                    classAttendanceId,
//                    "yashsahu@gmail.com",
//                    "success",
//                )
//            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {
            realmModule.isSynced.observe(viewLifecycleOwner) { isSynced ->
                if (isSynced) {
                    Log.d("Sync Update :: ", "------- Sync COMPLETED ------- ")
                    val fetchedCourses = CourseRepository().getAllCourse()
                    for (course in fetchedCourses) {
                        var alreadyExists = false
                        for (existingCourse in courseList) {
                            if (course._id == existingCourse._id) {
                                alreadyExists = true
                                break
                            }
                        }
                        if (!alreadyExists) {
                            courseList.add(course)
                        }
                    }
                    Log.d("Course ::: ", "Course list hai bhai ----> $courseList")
                    Log.d(
                        "Course ::: ",
                        "Course hai course hai ----> ${CourseRepository().getAllCourse()}"
                    )
//                 Do something when the Realm data is synced
                } else {
                    // Do something when the Realm data is not synced yet
                    Log.d("Sync Update :: ", "------- Sync NOT-COMPLETED ------- ")
                }
            }
        }

        Log.d(
            "Course ::: ",
            "Course hai course hai ----> ${CourseRepository().getAllCourse()}"
        )
    }

}