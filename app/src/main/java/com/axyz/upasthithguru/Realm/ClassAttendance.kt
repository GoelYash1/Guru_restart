package com.axyz.upasthithguru.Realm

import android.util.Log
import com.axyz.upasthithguru.app
import com.axyz.upasthithguru.data.realmModule
import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.mongodb.ext.customDataAsBsonDocument
import io.realm.kotlin.mongodb.ext.profileAsBsonDocument
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
    var student_id: ObjectId = ObjectId()
    var emailId: String =""
    var classNumber:Int = 0
    var isPresent: Boolean = false
    var timeOfAttendance: String=""
}



@Singleton
class ClassAttendanceManager {
//    private val attendances = ClassAttendance()
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
    fun createAttendanceRecord(_id: ObjectId):Int {
        val realm = realmModule.realm
        var ttlClasses = 0;
        realm.writeBlocking {
            val course = this.query<Course>("_id == $0", _id ).first().find()
            course?.apply {
                this.totalNoOfClasses = this.totalNoOfClasses+1
            }
            ttlClasses= course?.totalNoOfClasses!!
//            course?.courseAttendances?.add(classAttendance)
//            classAttendance?.courseAttendances?.add(course)

        }
//        classAttendance.backlinks(Course::courseAttendances)
//        Log.d("Course Created ::","courese ${classAttendance._id}")
//        return classAttendance._id
        return ttlClasses
    }

    suspend fun addStudentRecord(classNo: Int, emailid: String, llogStatus:String) {
        val realm = realmModule.realm
        realm.write{
//            val course = this.query<Course>("_id == $0",_id).first().find()
//            val classAttendanceFound = this.query<ClassAttendance>("_id == $0",_id).first().find()
            StudentRecord().apply {
                student_id = app.currentUser?.id?.let { ObjectId(it) }!!
                classNumber = classNo
                emailId = emailid
                isPresent = true
                timeOfAttendance = Date().toString()
//                classAttendanceFound?.attendanceRecord?.add(this)
//                this.backlink(classAttendance)
            }
        }
    }

//    suspend fun getClassAttendanceRecord(_id: ObjectId): ClassAttendance? {
//        val realm = realmModule.realm
//        var classAttendance=realm.query<ClassAttendance>("_id == $0",_id).first().find()
//        return classAttendance
//    }

    suspend fun getAllStudentRecords(){
        val realm = realmModule.realm
//        var enrolledStudents = realm.query<StudentRecord>().find()
//        var users = realm.query<UserRole>().find()

//        var enrolledStudents = realm.query<ClassAttendance>().find()
//        val isenrolled = EnrollStudentsManager().enrollStudent()
//        this.copyToRealm(course)
//        val user = realm.query<UserRole>("user_id == $0",
//            app.currentUser?.id?.let { ObjectId(it) }).first().find()
//        var courses = realm.query<Course>().find()
//        app.currentUser?.refreshCustomData()
//        val userCourses = app.currentUser?.customDataAsBsonDocument()?.getArray("courses")
//        userCourses?.forEach{
////            val isenrolled = EnrollStudentsManager().enrollStudent(it)
////            Log.d("Added Class Attendences- $it","${realm.query<ClassAttendance>("courseIdCA == $0", it)}")
//        }
//        val addedCourses = relam.query<ClassAttendance>("courseIdCA == $0", it)
//        val functionResponse = app.currentUser?.functions
//            ?.call<BsonBoolean>("define_access",courses[0]._id)
//        Log.d("user", "user usha function :: ${functionResponse}")
//        Log.d("class attendande ---  ::","$enrolledStudents")
//        Log.d(" Users ---  ::","$users")
//        Log.d(" Courses uses ke khudke  ::","$userCourses")
//        Log.d(" Courses ---  ::","$courses")
//        app.currentUser.functions
//        Log.d("Current User ---  ::","${app.currentUser?.profileAsBsonDocument()}")
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

