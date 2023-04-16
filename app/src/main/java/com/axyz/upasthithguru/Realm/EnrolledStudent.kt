package com.axyz.upasthithguru.Realm

import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class EnrolledStudent : RealmObject {
    @PrimaryKey
    var _id: ObjectId = BsonObjectId()
    var studentEmail: String =""
    var invitationStatus: String=""
    var studentId: String=""
    val course: RealmResults<Course> by backlinks (Course::enrolledStudents)
}