package com.axyz.upasthithguru.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.Realm.EnrollStudentsManager
import com.axyz.upasthithguru.Realm.InvitationRecord
import com.axyz.upasthithguru.app
import com.axyz.upasthithguru.data.realmModule
import com.axyz.upasthithguru.data.realmModule.realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.mongodb.ext.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mongodb.kbson.BsonBoolean
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class StudentsEnrolled : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_enrolled)
        val enrollStudents = findViewById<Button>(R.id.btn_enrollStudents)
        val passedId = intent.getByteArrayExtra("Course Id")?.let { BsonObjectId(it) }!!
//        courseId = passedId
        enrollStudents.setOnClickListener {
//            startActivity(Intent(this,EnrollStudentForm::class.java))
//            EnrollStudentsManager().enrollStudent(passedId,"y@gmail.com","Yash")
//            EnrollStudentsManager().getAllEnrolledStudent()
//            CoroutineScope()
            CoroutineScope(Dispatchers.Main).launch {
//                ClassAttendanceManager().getAllStudentRecords()
//                EnrollStudentsManager().sendEnrollInvitation(passedId,"yss@gmail.com")
//                Log.d("Passed Id Hex::", " ${ObjectId(passedId.toHexString())}")

                var invitations  = realmModule.realm.query<InvitationRecord>().find()
                Log.d("Invitations ::","${invitations}")


//        val addedCourses = realm.query<InvitationRecord>("courseIdCA == $0", it)


//                Log.d("Enrolled Ho gya ::","${isenrolled}")
            }
//            ClassAttendanceManager().getAllStudentRecords()
//            Log.d("Enrolled Students ::","${app.currentUser}")

        }
    }
}