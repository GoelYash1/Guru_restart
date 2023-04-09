package com.axyz.upasthithguru.domain


import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class UserRole() : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var role: String = ""
    var owner: String = ""
}
