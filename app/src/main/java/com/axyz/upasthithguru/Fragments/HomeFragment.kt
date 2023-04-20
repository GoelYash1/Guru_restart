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
import android.widget.SearchView
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
import io.realm.kotlin.mongodb.ext.customDataAsBsonDocument
import io.realm.kotlin.mongodb.ext.profileAsBsonDocument
import io.realm.kotlin.mongodb.subscriptions
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class Home : Fragment() {
//    @Inject
//    lateinit var courseRepository: CourseRepository
    var courseList : RealmList<Course> = realmListOf<Course>()
    val courseListAdapter = CourseListAdapter(courseList)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.homeRecView)
        val searchView = view.findViewById<androidx.appcompat.widget.SearchView>(R.id.search_course)
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
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = courseListAdapter

        courseListAdapter.setOnItemClickListener(object : CourseListAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val id = courseList[position]._id.toByteArray()
                val intent = Intent(this@Home.requireContext(), CourseInfo::class.java)
                intent.putExtra("Course Id",id)
                startActivity(intent)
            }
        })

        view.findViewById<ImageView>(R.id.homeProfileImageView).setOnClickListener{
            val intent = Intent(this@Home.requireContext(), Profile::class.java)
            startActivity(intent)
        }
        view.findViewById<CardView>(R.id.addNewCourseCard).setOnClickListener {
//            CoroutineScope(Dispatchers.Main).launch {
//                ClassAttendanceManager().getAllStudentRecords()
//                app.currentUser?.refreshCustomData()
//                Log.d("Enrolled Students ::","${app.currentUser?.profileAsBsonDocument()}")
//                Log.d("Enrolled Students Refresh ","${app.currentUser?.refreshCustomData()}")
//                Log.d("Enrolled Students :: ---","${app.currentUser?.functions?.user?.customDataAsBsonDocument()}")
//
//            }
            CoroutineScope(Dispatchers.Main).launch {
                ClassAttendanceManager().getAllStudentRecords()
            }
            val intent = Intent(this@Home.requireContext(), AddNewCourse::class.java)
            startActivity(intent)
//            var classAttendanceId = ClassAttendanceManager().createAttendanceRecord(courseList.first()._id)

        }
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
        return view
    }
    private fun filterList(query : String?){
        if(query!=null){
            val filteredList = kotlin.collections.ArrayList<Course>()
            val formattedQuery = query.replace("\\s".toRegex(), "") // Remove all spaces from the search query
            for (course in courseList) {
                val formattedCourseName = course.name.toString().replace("\\s".toRegex(), "") // Remove all spaces from the course name
                if (formattedCourseName.lowercase().contains(formattedQuery.lowercase())) {
                    filteredList.add(course)
                }
            }
            courseListAdapter.setFilteredList(filteredList)
        }
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
                    Log.d("Course ::: ", "Course user BSon ----> ${app.currentUser?.customDataAsBsonDocument()}")
                    Log.d(
                        "Course ::: ",
                        "Course hai course hai ----> ${CourseRepository().getAllCourse()}"
                    )
//                 Do something when the Realm data is synced
                    courseListAdapter.notifyDataSetChanged()
                } else {
                    // Do something when the Realm data is not synced yet
                    Log.d("Sync Update :: ", "------- Sync NOT-COMPLETED ------- ")
                }
            }
        }
//
//        Log.d(
//            "Course ::: ",
//            "Course hai course hai ----> ${CourseRepository().getAllCourse()}"
//        )
    }

}