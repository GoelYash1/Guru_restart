package com.axyz.upasthithguru.Realm
//
//import android.util.Log
//import io.realm.kotlin.Realm
//import io.realm.kotlin.RealmConfiguration
//import io.realm.kotlin.ext.query
//import io.realm.kotlin.query.RealmResults
//import io.realm.kotlin.types.RealmObject
//import io.realm.kotlin.types.annotations.PrimaryKey
//
//open class Student() : RealmObject{
//    @PrimaryKey var id : String = ""
//    private var emailId: String = ""
//    var name: String = ""
//    var batch: String = ""
//    var branch: String = ""
//    var rollNo: String = ""
//    var courses : MutableList<String> = mutableListOf()
//
//    constructor(id: String,emailId: String, name: String, batch: String, branch: String, rollNo: String) : this() {
//        this.id=id
//        this.emailId = emailId
//        this.name = name
//        this.batch = batch
//        this.branch = branch
//        this.rollNo = rollNo
//    }
//}
//
//class StudentManager {
//    private val config = RealmConfiguration.Builder(schema = setOf(Student::class, Course::class,Address::class ))
//        .build()
//    private val realm = Realm.open(config)
//
//    fun insertStudent(student: Student) {
//        try {
//            realm.writeBlocking {
//                this.copyToRealm(student)
//            }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Inserting Student Failed - $e")
//        }
//    }
//
//    fun updateStudent(
//        emailId: String,
//        name: String? = null,
//        batch: String? = null,
//        branch: String? = null,
//        rollNo: String? = null
//    ) {
//        try {
//            realm.writeBlocking {
//                var student: Student? = this.query<Student>("_id == $0", emailId).first().find()
//                if(student != null ){
//                    if (name != null) { student.name = name }
//                    if (batch != null) { student.batch = batch }
//                    if (branch != null) { student.branch = branch }
//                    if (rollNo != null) { student.rollNo = rollNo }
//                    Log.d("Database Update :: ", "Student Details Updated Successfully")
//
//                }else{
//                    Log.d("Database Error :: ", "No Student Found For the Given ID")
//                }
//
//            }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Updating Student Failed - $e")
//        }
//    }
//
//    fun registerStudentCourse(emailId: String,courseId:String){
//        try {
//            realm.writeBlocking {
//                var student: Student? = this.query<Student>("_id == $0", emailId).first().find()
//                if(student != null ){
//                    student.courses.add(courseId)
//                    Log.d("Database Update :: ", "Student Details Updated Successfully")
//                }else{
//                    Log.d("Database Error :: ", "No Student Found For the Given ID")
//                }
//
//            }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Updating Student Failed - $e")
//        }
//    }
//
//    fun deregisterStudentCourse(emailId: String,courseId:String){
//        try {
//            realm.writeBlocking {
//                var student: Student? = this.query<Student>("_id == $0", emailId).first().find()
//                if(student != null ){
//                    student.courses.remove(courseId)
//                    Log.d("Database Update :: ", "Student Details Updated Successfully")
//                }else{
//                    Log.d("Database Error :: ", "No Student Found For the Given ID")
//                }
//
//            }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Updating Student Failed - $e")
//        }
//    }
//
//    fun deleteStudent(id: String) {
//        try {
//            realm.writeBlocking {
//                val student: Student? =
//                    this.query<Student>("_id == $0", id).first().find()
//                if (student != null) {
//                    delete(student)
//                } else {
//                    Log.d("Database Error :: ", "Deleting Student Failed - STUDENT NOT FOUND")
//                }
//            }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Deleting Student Failed - $e")
//        }
//    }
//
//    fun getStudent(id: String): Student? {
//        return try {
//            realm.query<Student>("_id == $0", id).first().find()
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Failed to Get Student - $e")
//            null
//        }
//    }
//
//    fun getAllStudents(): RealmResults<Student> {
//        return realm.query<Student>().find()
//    }
//}
