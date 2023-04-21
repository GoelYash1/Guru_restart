package com.axyz.upasthithguru.Realm

import android.util.Log
import com.axyz.upasthithguru.app
import com.axyz.upasthithguru.data.realmModule
import com.axyz.upasthithguru.data.realmModule.realm
import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.ext.query
import io.realm.kotlin.mongodb.ext.customDataAsBsonDocument
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId
import java.util.*
import javax.inject.Singleton

class InvitationRecord() : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var studentEmailId: String =""
    var courseId:String = ""
    var courseName: String=""
    var invitedByTeacherEmail: String=""
    var status: String="sent"
    var timeOfInviting: String=""
}


@Singleton
class EnrollStudentsManager {
    //    fun enrollStudent(_id: ObjectId, studentEmail: String, studentName: String){

    fun sendEnrollInvitation(_id: ObjectId, studentEmail: String){
        realm.writeBlocking {
            val course = this.query<Course>("_id == $0", _id).first().find()
            val teacherEmail = app.currentUser?.customDataAsBsonDocument()?.getValue("email")?.asString()?.value.toString()
            var invite = InvitationRecord().apply {
                studentEmailId = studentEmail
                courseId = _id.toHexString()
                courseName = course?.name.toString()
                invitedByTeacherEmail = teacherEmail
                timeOfInviting = Date().toString()
            }
            this.copyToRealm(invite)
            Log.d("Invitation Sent", "Invitation Sent to $studentEmail to $invite")
        }
    }
    fun enrollStudent(_id: ObjectId, studentEmail: String) {

        val realm = realmModule.realm
//        var studentToEnroll = EnrolledStudent()
        var doesStudentExist = false
        realm.writeBlocking {
            val course = this.query<Course>("_id == $0", _id).first().find()
            doesStudentExist = course?.enrolledStudent?.any { it == studentEmail } == true
            if (doesStudentExist != true) {
//                studentToEnroll = EnrolledStudent().apply {
//                    name = studentName
//                    email = studentEmail
//                    date = Date().toString()
//                }
                val studend_id = app.currentUser?.id
                if (!studend_id.isNullOrBlank()) {
                    course?.enrolledStudents?.add(ObjectId(studend_id))
                }
//            }
            }
            if (doesStudentExist) {
                Log.d("Error ::", "Student Already Exits")
            }
        }

        fun getAllEnrolledStudent() {
//        val realm = realmModule.realm
//        var enrolledStudents = realm.query<EnrolledStudent>().find()
//        Log.d("Enrolled Students ::","$enrolledStudents")
            Log.d("Enrolled Students ::", "${app.currentUser}")
//        enrolledStudents.

        }
    }
}
