package com.axyz.upasthithguru.Realm

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.Date

data class StudentRecord(
    var emailId: String,
    var isPresent: Boolean,
    var logStatus: String,
    var timeOfAttendance: Date
)

//data class ClassAttendance(
//    var date: Date,
//    var attendanceRecord: MutableList<StudentRecord> = mutableListOf()
//)

class ClassAttendance : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.create()
    var date: String = ""
//    var attendanceRecord: MutableList<StudentRecord> = mutableListOf()
//    val courseAttendance: RealmResults<Course> by backlinks(Course::courseAttendances)
}


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

//    fun addStudentRecord(StudentRecord: StudentRecord) {
//        if(!attendances.attendanceRecord.contains(StudentRecord)){
//            attendances.attendanceRecord.add(StudentRecord)
//        }
//    }
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

