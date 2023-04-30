package com.axyz.upasthithguru.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.axyz.upasthithguru.PermissionUtils
import com.axyz.upasthithguru.databinding.ActivityTakeAttendanceBinding
import com.axyz.upasthithguru.permissionDialogShown
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.mongodb.kbson.ObjectId
import java.text.SimpleDateFormat
import java.util.*

class TakeAttendance : AppCompatActivity() {
    private var currentPin: Int = 0
    private lateinit var binding: ActivityTakeAttendanceBinding
    private lateinit var courseId:ObjectId
    private var isPinGenerated: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val time: TextView = binding.takeAttendanceCurrentTime
        courseId = intent.getByteArrayExtra("Course Id")?.let { ObjectId(it) }!!
        // Create a Date object with the current time
        val currentTime = Calendar.getInstance().time

        // Create a date formatter with the desired time format
        val timeFormatter = SimpleDateFormat("h:mm a", Locale.getDefault())

        // Format the current time with the formatter and set it to the TextView
        val formattedTime = timeFormatter.format(currentTime)
        time.text = formattedTime

        // Create a date formatter with the desired date format
        val dateFormatter = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())

        // Format the current date with the formatter and set it to the TextView
        val formattedDate = dateFormatter.format(currentTime)
        val date: TextView = binding.takeAttendanceCurrentDayAndDate
        date.text = formattedDate

        val backButton: Button = binding.takeAttendanceBackButton
        backButton.setOnClickListener {
            finish()
        }
        val generatedPinText:TextView = binding.generatedPinTextView
        val generatePinFAB: FloatingActionButton = binding.generatePinButton
        val attendanceState: TextView = binding.takeAttendanceState
        var generatedPin : String = String()
        attendanceState.text = "Fetching Records State"
        generatePinFAB.setOnClickListener{
            if (isPinGenerated) {
                Log.d("MY_APP_TAG", "Pin already generated")
                return@setOnClickListener
            }
            generatedPin = generateNewPin()
            Log.d("MY_APP_TAG", "generatedText: $generatedPin")

            generatePinFAB.setImageDrawable(null)
            generatedPinText.text = generatedPin
            val params = generatedPinText.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(params.leftMargin,0,params.rightMargin,params.bottomMargin)
            generatedPinText.layoutParams = params
            attendanceState.text = "Unsupportive State"
            isPinGenerated = true

            // Disable the button after it has been clicked once
            generatePinFAB.isEnabled = false
        }

        val startAttendance: Button = binding.takeAttendanceStartAttendanceButton

        startAttendance.setOnClickListener{
            if (!isPinGenerated) {
                Log.d("MY_APP_TAG", "Pin not generated yet")
                return@setOnClickListener
            }
            val intent = Intent(this, StartAttendance::class.java)
            intent.putExtra("Generated Pin",generatedPin)
            intent.putExtra("Class Attendance Id",courseId.toByteArray())
            startActivity(intent)
            finish()
//            val handler = Handler(Looper.getMainLooper())
//            progressBar.visibility = View.VISIBLE
//            handler.postDelayed({
//                progressBar.visibility = View.INVISIBLE
//                generatedPinText.visibility = View.GONE
//                generatePinFAB.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_right_tick))
//                attendanceState.text = "Attendance Taken"
//            }, 10*1000)
        }
    }
    private fun generateNewPin(): String {
        val random = Random()
        currentPin = random.nextInt(9000) + 1000
        return currentPin.toString()
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

