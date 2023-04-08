package com.axyz.upasthithguru.Realm

import android.util.Log
import com.axyz.upasthithguru.data.realmModule
import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.util.Date
import javax.inject.Singleton

class StudentRecord : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var emailId: String =""
    var isPresent: Boolean=false
    var logStatus: String=""
    var timeOfAttendance: String=""
    val classAttendance: RealmResults<ClassAttendance> by backlinks (ClassAttendance::attendanceRecord)
}

//data class ClassAttendance(
//    var date: Date,
//    var attendanceRecord: MutableList<StudentRecord> = mutableListOf()
//)

class ClassAttendance : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var date: String = ""
    var attendanceRecord: RealmList<StudentRecord> = realmListOf()
    val courseAttendance: RealmResults<Course> by backlinks(Course::courseAttendances)
}

@Singleton
class ClassAttendanceManager {
    private val attendances = ClassAttendance()
//    private val config = RealmConfiguration.Builder(schema = setOf(Course::class))
//        .build()
//    private val realm = Realm.open(config)
//    suspend fun example(){
//        realm.write {
//            val course = copyToRealm(Course())
//            course.apply {
//                name = "Software Defined Networking"
//                // Embed the address in the contact object
//                var classRec = ClassAttendance().apply {
//                    date = Date(System.currentTimeMillis())
//                    ex = "hello"
//                }
//            }
//        }
//    }
    fun createAttendanceRecord(_id: String):ObjectId {
        val realm = realmModule.realm
        var classAttendance = ClassAttendance()
        realm.writeBlocking {
            val objectId = ObjectId(_id.encodeToByteArray()) // or ObjectId("5f1f3c7e7c8d2d2a60e9b4f3")
            val course = this.query<Course>("_id == $0", objectId ).first().find()
            classAttendance = ClassAttendance().apply {
                date = Date().toString()
            }
            course?.courseAttendances?.add(classAttendance)
        }
        Log.d("Course Created ::","courese ${classAttendance._id}")
        return classAttendance._id
    }

    fun addStudentRecord(_id:ObjectId,emailId: String,logStatus:String) {
        val realm = realmModule.realm
        realm.writeBlocking {
//            val course = this.query<Course>("_id == $0",_id).first().find()
            val classAttendance = this.query<ClassAttendance>("_id == $0",_id).first().find()
            val studentRecord = StudentRecord().apply {
                this.emailId = emailId
                this.isPresent = true
                this.logStatus = logStatus
                this.timeOfAttendance = Date().toString()
            }
            classAttendance?.attendanceRecord?.add(studentRecord)
        }
    }
//
//    fun removeStudentRecord(StudentRecord: StudentRecord) {
//        attendances.attendanceRecord.remove(StudentRecord)
//    }
//
//    fun updateStudentRecord(StudentRecord: StudentRecord) {
//        val index = attendances.attendanceRecord.indexOf(StudentRecord)
//        if (index != -1) {
//            attendances.attendanceRecord[index] = StudentRecord
//        }
//    }
//
//    fun getStudentRecord(emailId: String): StudentRecord? {
//        return attendances.attendanceRecord.find { it.emailId == emailId }
//    }
//
//    fun getAttendance(): ClassAttendance {
//        return attendances
//    }
}

