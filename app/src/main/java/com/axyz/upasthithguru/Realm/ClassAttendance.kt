package com.axyz.upasthithg.Realm

import com.axyz.upasthithguru.Realm.Course

import android.util.Log
import com.axyz.upasthithguru.Realm.InvitationRecord
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
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

class StudentRecord : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var markedByTeacherId: String = ""
    var courseId: String=""
    var studentEmailId: String =""
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
        Log.d("Incrementing","Incrementing the  Class No.")
        realm.writeBlocking {
            val course = this.query<Course>("_id == $0", _id ).first().find()
            course?.totalNoOfClasses = course?.totalNoOfClasses?.plus(1)!!
            Log.d("Updated Course ::","${course?.totalNoOfClasses}")
        }
        return ttlClasses
    }

    suspend fun addStudentRecord(_id: ObjectId,classNo: Int, emailid: String) {
        val realm = realmModule.realm
        realm.write{
            val course = this.query<Course>("_id == $0",_id).first().find()
//            val classAttendanceFound = this.query<ClassAttendance>("_id == $0",_id).first().find()
            val searchStudent = this.query<StudentRecord>("courseId == $0 AND classNumber == $1 AND studentEmailId == $2",_id.toHexString(),classNo,emailid).first().find()
            if(searchStudent?.studentEmailId != emailid){
                val student = StudentRecord().apply {
                    markedByTeacherId = course?.createdByInstructor.toString()
                    courseId = _id.toHexString()
                    classNumber = classNo
                    studentEmailId = emailid
                    isPresent = true
                    timeOfAttendance = Date().toString()
                }
                this.copyToRealm(student)
            }else{
                Log.d("ATTENDANCE UPDATE","ALready marked for the student with id $emailid")
            }
        }
    }

//    suspend fun getClassAttendanceRecord(_id: ObjectId): ClassAttendance? {
//        val realm = realmModule.realm
//        var classAttendance=realm.query<ClassAttendance>("_id == $0",_id).first().find()
//        return classAttendance
//    }

//    fun getAllStudentRecords(courseId:String): RealmResults<InvitationRecord> {
    fun getAllStudentRecords(courseId:String): List<Map<String,Any>> {

            val realm = realmModule.realm
//          var enrolledStudents = realm.query<InvitationRecord>("courseId == $0",courseId).find()
            val totalNoLecctures = realm.query<Course>("_id == $0",ObjectId(courseId)).first().find()?.totalNoOfClasses
            Log.d("Total No of Lectures ::","$totalNoLecctures")
            val attendanceOfClass = realm.query<StudentRecord>("courseId == $0",courseId).find()
            Log.d("Attendance of Class ::","$attendanceOfClass")
            val enrolledStudents = realm.query<InvitationRecord>("courseId == $0 AND status == $1",courseId,"accepted".toString()).find()
            Log.d("Enrolled Students ::","$enrolledStudents")
            val studentAttendanceRecord :MutableMap<String,Int> = mutableMapOf()

            attendanceOfClass.forEach{student ->
                if(student.isPresent){
                    if(studentAttendanceRecord.containsKey(student.studentEmailId)){
                        studentAttendanceRecord[student.studentEmailId] = studentAttendanceRecord[student.studentEmailId]!!.plus(1)
                    }else{
                        studentAttendanceRecord[student.studentEmailId] = 1
                    }
                }
            }
            enrolledStudents.forEach{ student ->
                    if(!studentAttendanceRecord.containsKey(student.studentEmailId)){
                        studentAttendanceRecord[student.studentEmailId] = 0
                    }
            }
            val record = mutableListOf<Map<String,Any>>()
            Log.d("Student Attendance Record ::","$studentAttendanceRecord")
            studentAttendanceRecord.forEach{ (key,value) ->
                var attendancePercentage : Int
                if(totalNoLecctures != 0){
                    attendancePercentage = (value*100)/totalNoLecctures!!
                }else{
                    attendancePercentage = (value*100)/1
                }
                val studentRecord = mapOf("email" to key,"attendance" to value,"attendancePercentage" to attendancePercentage)
                record.add(studentRecord)
            }
            Log.d("Student Record ::","$record")
        return record
    }

    fun getAllInvitedStudentRecords(courseId:String): List<Map<String,Any>> {

        val realm = realmModule.realm
        val totalNoLecctures = realm.query<Course>("_id == $0",ObjectId(courseId)).first().find()?.totalNoOfClasses
        Log.d("Total No of Lectures ::","$totalNoLecctures")
        val enrolledStudents = realm.query<InvitationRecord>("courseId == $0 AND status == $1",courseId,"sent".toString()).find()
        Log.d("Enrolled Students ::","$enrolledStudents")

        val record = mutableListOf<Map<String,Any>>()
        enrolledStudents.forEach{ student ->
            val studentRecord = mapOf("email" to student.studentEmailId,"status" to student.status)
            record.add(studentRecord)
        }
        Log.d("Student Invited Record ::","$record")

        return record

//        [
//            {
//                "email": "y@gmail.com",
//                "attendance": 2,
//                "attendancePercentage": 50,
//            },
//            {
//                "email": "y@gmail.com",
//                "attendance": 2,
//                "attendancePercentage": 50,
//            },
//        ]
    }

    fun getAllStudentRecordsOfParticularDate(courseId:String,targetDate: String): List<StudentRecord> {
        val realm = realmModule.realm
        val totalNoLecctures = realm.query<Course>("_id == $0",ObjectId(courseId)).first().find()?.totalNoOfClasses
        Log.d("Total No of Lectures ::","$totalNoLecctures")
        val attendanceOfClass = realm.query<StudentRecord>("courseId == $0",courseId).find()
        Log.d("Attendance of Class ::","$attendanceOfClass")

//        val targetDate = "2023-04-27" // replace with your target date string in yyyy-MM-dd format
//        Filtering on the basis of the Date in the format YYYY-MM-DD
        val filteredList = attendanceOfClass.filter {
            val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
            val date = dateFormat.parse(it.timeOfAttendance)
            val dateStr = SimpleDateFormat("yyyy-MM-dd").format(date)
            dateStr == targetDate
        }
        Log.d("Filtered List ::","${filteredList[0].timeOfAttendance}")
        return filteredList
    }

    fun getAttendancePercentageAndClassInfoOfMonth(courseId: String, targetMonthYear: String): List<StudentRecord> {
        val realm = realmModule.realm
        val totalNoLectures = realm.query<Course>("_id == $0", ObjectId(courseId)).first().find()?.totalNoOfClasses
        Log.d("Total No of Lectures ::", "$totalNoLectures")
        val attendanceOfClass = realm.query<StudentRecord>("courseId == $0", courseId).find()
        Log.d("Attendance of Class ::", "$attendanceOfClass")

        // Filter the attendance records for the target month and year yyyy-MM
        val filteredListOfMonth = attendanceOfClass.filter {
            val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
            val date = dateFormat.parse(it.timeOfAttendance)
            val cal = Calendar.getInstance()
            cal.time = date
            val recordMonthYear = "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH) + 1}"
            recordMonthYear == targetMonthYear
        }
        val record = mutableMapOf<String, MutableList<MutableMap<String, Any>>>()
        var currDate = 1
        var fullCurrentDate = targetMonthYear+"-"+currDate.toString()
        filteredListOfMonth.forEach(){
            val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
            val date = dateFormat.parse(it.timeOfAttendance)
            val dateStr = SimpleDateFormat("yyyy-MM-dd").format(date)
            if (record.containsKey(dateStr)) {
                val nestedMap = mutableMapOf<String, Any>("classNumber" to it.classNumber, "percentage" to 50.2)
                record[dateStr]?.add(nestedMap)
            } else {
                val nestedList = mutableListOf<MutableMap<String, Any>>(mutableMapOf("classNumber" to it.classNumber, "percentage" to 45.6))
                record[dateStr] = nestedList
            }
        }
        for ((date, list) in record) {
            println("$date:")
            for (nestedMap in list) {
                println("\t$nestedMap")
            }
        }
//        {
//            "2021-04-01": [
//                {
//                    "classNumber": 2,
//                    "percentage": 50.2,
//                },
//            ],
//            "2021-04-01": [
//                {
//                    "classNumber": 2,
//                    "percentage": 50.2,
//                },
//            ],
//        }

        Log.d("Filtered List ::", "${filteredListOfMonth.size} records")
        return filteredListOfMonth
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

