package com.axyz.upasthithguru.Realm

import android.util.Log
import com.axyz.upasthithguru.app
import com.axyz.upasthithguru.data.realmModule
import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.mongodb.ext.call
import io.realm.kotlin.mongodb.ext.profileAsBsonDocument
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonDocument
import org.mongodb.kbson.ObjectId
import java.util.Date
import javax.inject.Singleton

class StudentRecord : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var emailId: String =""
    var isPresent: Boolean = false
    var logStatus: String=""
    var timeOfAttendance: String=""
    val classAttendance: RealmResults<ClassAttendance> by backlinks (ClassAttendance::attendanceRecord)
}

class ClassAttendance : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var date: String = ""
    var courseIdCA : ObjectId? = null
    var attendanceRecord: RealmList<StudentRecord> = realmListOf()
    val courseAttendances: RealmResults<Course> by backlinks(Course::courseAttendances)
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
    fun createAttendanceRecord(_id: ObjectId):ObjectId {
        val realm = realmModule.realm
        var classAttendance = ClassAttendance()
        realm.writeBlocking {
//            val objectId = ObjectId(_id.encodeToByteArray()) // or ObjectId("5f1f3c7e7c8d2d2a60e9b4f3")
            val course = this.query<Course>("_id == $0", _id ).first().find()
            classAttendance = ClassAttendance().apply {
                date = Date().toString()
                courseIdCA = _id
            }
            course?.courseAttendances?.add(classAttendance)
//            classAttendance?.courseAttendances?.add(course)

        }
        classAttendance.backlinks(Course::courseAttendances)
        Log.d("Course Created ::","courese ${classAttendance._id}")
        return classAttendance._id
    }

    suspend fun addStudentRecord(_id:ObjectId, emailid: String, llogStatus:String) {
        val realm = realmModule.realm
        realm.write{
//            val course = this.query<Course>("_id == $0",_id).first().find()
            val classAttendanceFound = this.query<ClassAttendance>("_id == $0",_id).first().find()
            val studentRecord = StudentRecord().apply {
                emailId = emailid
                isPresent = true
                logStatus = llogStatus
                timeOfAttendance = Date().toString()
                classAttendanceFound?.attendanceRecord?.add(this)
//                this.backlink(classAttendance)
            }
            studentRecord.backlinks(ClassAttendance::attendanceRecord)
        }
    }

    suspend fun getClassAttendanceRecord(_id: ObjectId): ClassAttendance? {
        val realm = realmModule.realm
        var classAttendance=realm.query<ClassAttendance>("_id == $0",_id).first().find()
        return classAttendance
    }

    suspend fun getAllStudentRecords(){
        val realm = realmModule.realm
//        var enrolledStudents = realm.query<StudentRecord>().find()
//        var users = realm.query<UserRole>().find()

        var enrolledStudents = realm.query<ClassAttendance>().find()
//        this.copyToRealm(course)
//        val user = realm.query<UserRole>("user_id == $0",
//            app.currentUser?.id?.let { ObjectId(it) }).first().find()
        var courses = realm.query<Course>().find()
//        val functionResponse = app.currentUser?.functions
//            ?.call<BsonDocument>("addCourseToUser",ObjectId())
//        Log.d("user", "user usha function :: ${functionResponse}")
        Log.d("class attendande ---  ::","$enrolledStudents")
//        Log.d(" Users ---  ::","$users")
        Log.d(" Courses ---  ::","$courses")
        Log.d(" Courses ---  ::","$courses")
//        app.currentUser.functions
        Log.d("Current User ---  ::","${app.currentUser?.profileAsBsonDocument()}")
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

