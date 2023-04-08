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
//
//open class Instructor() : RealmObject {
//    @PrimaryKey var id: String = ""
//    private var emailId: String = ""
//    var name: String = ""
//    var description: String = ""
//    var courses : MutableList<String> = mutableListOf()
//
//    constructor(id:String,emailId: String, name: String, description: String) : this() {
//        this.id = id
//        this.emailId = emailId
//        this.name = name
//        this.description = description
//    }
//}
//
//class InstructorManager {
//    private val config = RealmConfiguration.Builder(schema = setOf(Instructor::class))
//        .build()
//    private val realm = Realm.open(config)
//
//    fun insertInstructor(instructor: Instructor) {
//        try {
//            realm.writeBlocking {
//                this.copyToRealm(instructor)
//            }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Inserting Instructor Failed - $e")
//        }
//    }
//
//    fun updateInstructor(
//        emailId: String,
//        name: String? = null,
//        description: String? = null,
//    ) {
//        try {
//            realm.writeBlocking {
//                var instructor: Instructor? =
//                    this.query<Instructor>("_id == $0", emailId).first().find()
//                if (instructor != null) {
//                    if (name != null) {
//                        instructor.name = name
//                    }
//                    if (description != null) {
//                        instructor.description = description
//                    }
//                    Log.d("Database Update :: ", "Instructor details updated successfully")
//                } else {
//                    Log.d("Database Error :: ", "No Instructor Found for the given ID")
//                }
//            }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Updating Instructor Failed - $e")
//        }
//    }
//
//    fun addCourseToInstructor(courseId: String,id: String){
//        try {
//            realm.writeBlocking {
//                var instructor: Instructor? =
//                    this.query<Instructor>("_id == $0", id).first().find()
//                if (instructor != null) {
//                    instructor.courses.add(id)
//                    Log.d("Database Update :: ", "Instructor details updated successfully")
//                } else {
//                    Log.d("Database Error :: ", "No Instructor Found for the given ID")
//                }
//            }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Updating Instructor Failed - $e")
//        }
//    }
//
//    fun removeCourseFromInstructor(courseId: String,id: String){
//        try {
//            realm.writeBlocking {
//                var instructor: Instructor? =
//                    this.query<Instructor>("_id == $0", id).first().find()
//                if (instructor != null) {
//                    instructor.courses.remove(id)
//                    Log.d("Database Update :: ", "Instructor details updated successfully")
//                } else {
//                    Log.d("Database Error :: ", "No Instructor Found for the given ID")
//                }
//            }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Updating Instructor Failed - $e")
//        }
//    }
//
//    fun getAllCourses(id: String): MutableList<String>? {
//        return try {
//            realm.query<Instructor>("_id == $0", id).first().find()?.courses
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Updating Instructor Failed - $e")
//            null
//        }
//    }
//
//    fun deleteInstructor(id: String) {
//        try {
//            realm.writeBlocking {
//                val instructor: Instructor? =
//                    this.query<Instructor>("_id == $0", id).first().find()
//                if (instructor != null) {
//                    delete(instructor)
//                } else {
//                    Log.d("Database Error :: ", "Deleting Instructor Failed - Instructor NOT FOUND")
//                }
//            }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Deleting Student Failed - $e")
//        }
//    }
//
//    fun getInstructor(id: String): Instructor? {
//        return try {
//            realm.query<Instructor>("_id == $0", id).first().find()
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Failed to Get Instructor - $e")
//            null
//        }
//    }
//
//    fun getAllInstructor(): RealmResults<Instructor> {
//        return realm.query<Instructor>().find()
//    }
//}
//
