package com.axyz.upasthithguru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.axyz.upasthithguru.Realm.CourseRepository
import com.axyz.upasthithguru.activity.HomeActivity
import com.axyz.upasthithguru.activity.OnboardingActivity
import com.axyz.upasthithguru.other.CheckLogin
import dagger.hilt.android.AndroidEntryPoint
import io.realm.kotlin.mongodb.User
import kotlinx.serialization.Serializable

@Serializable
data class CharacteristicDataClass(val attendance: Boolean)

@Serializable
data class CharacteristicDataBroadcast(val RollNo: String?)

const val CustomCharUuid = "ff82240a-27c1-4661-a15a-be5a22a17256"
const val CustomServiceUuidPrefix = "f4f5f6f9"
var permissionDialogShown = 0


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentUser: User? = app.currentUser
        if (currentUser != null && currentUser.loggedIn) {
            Toast.makeText(this,"User Already Signed In", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }
    }
}


