package com.axyz.upasthithguru.activity

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.Realm.EnrollStudentsManager
import com.axyz.upasthithguru.Realm.InvitationRecord
import com.axyz.upasthithguru.data.realmModule
import com.axyz.upasthithguru.databinding.ActivityStudentsEnrolledBinding
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mongodb.kbson.BsonObjectId

class StudentsEnrolledActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentsEnrolledBinding
    private lateinit var inviteNewStudent: AppCompatButton
    private lateinit var inviteNewStudentForm: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityStudentsEnrolledBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inviteNewStudent = binding.inviteNewStudent
        inviteNewStudentForm = binding.inviteNewStudentLayout
        val passedId = intent.getByteArrayExtra("Course Id")?.let { BsonObjectId(it) }!!
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
//            ClassAttendanceManager().getAllStudentRecords()
//            Log.d("Enrolled Students ::","${app.currentUser}")

        }
    }
}