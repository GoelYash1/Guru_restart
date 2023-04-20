package com.axyz.upasthithguru.Realm

import android.util.Log
import com.axyz.upasthithguru.app
import com.axyz.upasthithguru.data.realmModule
import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId
import java.util.*
import javax.inject.Singleton

class EnrolledStudent : RealmObject {
    @PrimaryKey
    var _id: ObjectId = BsonObjectId()
    var email: String =""
    var name: String =""
    var invitationStatus: String="sent"
    var date: String = ""
    var studentId: String=""
//    val course: RealmResults<Course> by backlinks (Course::enrolledStudents)
}
@Singleton
class EnrollStudentsManager {
//    fun enrollStudent(_id: ObjectId, studentEmail: String, studentName: String){
    fun enrollStudent(_id: ObjectId){

        val realm = realmModule.realm
        var studentToEnroll = EnrolledStudent()
        var doesStudentExist = false
        realm.writeBlocking {
            val course = this.query<Course>("_id == $0", _id ).first().find()
//            doesStudentExist = course?.enrolledStudents?.any { it.email == studentEmail } == true
//            if (doesStudentExist != true) {
//                studentToEnroll = EnrolledStudent().apply {
//                    name = studentName
//                    email = studentEmail
//                    date = Date().toString()
//                }
                val studend_id = app.currentUser?.id
                if(!studend_id.isNullOrBlank()){
                    course?.enrolledStudents?.add(ObjectId(studend_id))
                }
//            }
        }
        if(doesStudentExist){
            Log.d("Error ::","Student Already Exits")
        }
    }

    fun getAllEnrolledStudent(){
//        val realm = realmModule.realm
//        var enrolledStudents = realm.query<EnrolledStudent>().find()
//        Log.d("Enrolled Students ::","$enrolledStudents")
        Log.d("Enrolled Students ::","${app.currentUser}")
//        enrolledStudents.

    }
}
