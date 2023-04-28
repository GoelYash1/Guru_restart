package com.axyz.upasthithguru.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axyz.upasthithg.Realm.ClassAttendanceManager
import com.axyz.upasthithguru.Realm.EnrollStudentsManager
import com.axyz.upasthithguru.adapters.StudentListAdapter
import com.axyz.upasthithguru.databinding.ActivityStudentsEnrolledBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mongodb.kbson.BsonObjectId

class StudentsEnrolledActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentsEnrolledBinding
    private lateinit var inviteNewStudent: AppCompatButton
    private lateinit var inviteNewStudentForm: LinearLayout
    private lateinit var studentListView: RecyclerView
    private lateinit var studentList: List<Map<String,Any>>
    private lateinit var studentListAdapter: StudentListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityStudentsEnrolledBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inviteNewStudent = binding.inviteNewStudent
        inviteNewStudentForm = binding.inviteNewStudentLayout
        studentListView = binding.rvStudentList
        val passedId = intent.getByteArrayExtra("Course Id")?.let { BsonObjectId(it) }!!

        studentList = ClassAttendanceManager().getAllStudentRecords(passedId.toHexString())
        Log.d("Student List-->", studentList.size.toString())
        studentListAdapter = StudentListAdapter(studentList)
        studentListView.layoutManager = LinearLayoutManager(applicationContext)
        studentListView.adapter = studentListAdapter

        inviteNewStudent.setOnClickListener {
            if(inviteNewStudentForm.visibility == View.GONE)
            {
                inviteNewStudentForm.visibility = View.VISIBLE
                val emailEditText = binding.emailField
                val email = emailEditText.text
                val sendInvitationButton = binding.sendInvitationButton
                sendInvitationButton.setOnClickListener {
                    if (email.isEmpty()) {
                        // Display an error message if the email field is empty
                        Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                        emailEditText.error = "Please enter your email"
                    }
                    else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        // Display an error message if the email is not a valid email address
                        Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                        emailEditText.error = "Please enter a valid email"
                    }
                    else {
                        CoroutineScope(Dispatchers.Main).launch {
                            EnrollStudentsManager().sendEnrollInvitation(passedId, email.toString())
                            Toast.makeText(applicationContext,"Invitation has been sent to $email",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            else{
                inviteNewStudentForm.visibility = View.GONE
            }
//            startActivity(Intent(this,EnrollStudentForm::class.java))
//            EnrollStudentsManager().enrollStudent(passedId,"y@gmail.com","Yash")
//            EnrollStudentsManager().getAllEnrolledStudent()
//            CoroutineScope()
//            CoroutineScope(Dispatchers.Main).launch {
////                ClassAttendanceManager().getAllStudentRecords()
////                EnrollStudentsManager().sendEnrollInvitation(passedId,"yss@gmail.com")
////                Log.d("Passed Id Hex::", " ${ObjectId(passedId.toHexString())}")
//
//                var invitations  = realmModule.realm.query<InvitationRecord>().find()
//                Log.d("Invitations ::","${invitations}")
//
//
////        val addedCourses = realm.query<InvitationRecord>("courseIdCA == $0", it)
//
//
////                Log.d("Enrolled Ho gya ::","${isenrolled}")
//            }

        }
    }
}