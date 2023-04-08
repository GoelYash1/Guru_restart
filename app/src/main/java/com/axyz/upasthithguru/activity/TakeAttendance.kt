package com.axyz.upasthithguru.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.axyz.upasthithguru.PermissionUtils
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.permissionDialogShown
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class TakeAttendance : AppCompatActivity() {
    private lateinit var generatePinFAB: FloatingActionButton
    private lateinit var pinThatisGenerated: TextView
    private lateinit var generatedPin: TextView
    private lateinit var courseId:String
    private var currentPin: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_attendance)
        courseId = intent.getStringExtra("Course Id").toString()
        // Initialize the Different Views and Buttons
        generatePinFAB = findViewById(R.id.generatePinFAB)
        pinThatisGenerated = findViewById(R.id.pinThatIsGeneratedTextView)
        generatedPin = findViewById(R.id.generatedPinTextView)

        // Generate the initial random Pin
        generateNewPin()

        // Generate a new random Pin on button Click
        generatePinFAB.setOnClickListener {
            generateNewPin()
        }

        // Add the text to the TextViews
        pinThatisGenerated.text = currentPin.toString()
        generatedPin.text = currentPin.toString()

        val takeAttendanceButton = findViewById<Button>(R.id.takeAttendanceButton)
        takeAttendanceButton.setOnClickListener {
            if (currentPin == 0) {
                Toast.makeText(this, "Generate a pin first to continue", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, StartAttendance::class.java)
                Toast.makeText(this,"CourseId$courseId",Toast.LENGTH_SHORT).show()
                intent.putExtra("Pin", currentPin.toString())
                intent.putExtra("Course Id",courseId)
                startActivity(intent)
            }
        }
    }

    private fun generateNewPin() {
        val random = Random()
        currentPin = random.nextInt(9000) + 1000
        pinThatisGenerated.text = currentPin.toString()
        generatedPin.text = currentPin.toString()
    }

    // Handle the result of the permissions request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtils.onRequestPermissionsResult(this, permissions, grantResults)
    }

    // Handle the resume event of the activity
    override fun onResume() {
        super.onResume()
        Log.d("RESUME", "On Resume Called --- $permissionDialogShown")
        // Check if the app has all the necessary permissions
        if (!PermissionUtils.hasPermissions(this) && permissionDialogShown < 1) {
            // If not, request the missing permissions
            PermissionUtils.requestPermissions(this)
            Log.d("Permissions -> ", "All Not Permission Given -> ")
        } else {
            Log.d("Permissions -> ", "All Permission Given")
        }
    }
}
