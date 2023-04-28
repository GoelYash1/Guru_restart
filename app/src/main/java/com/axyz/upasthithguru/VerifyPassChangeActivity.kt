package com.axyz.upasthithguru

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class VerifyPassChangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_pass_change)
        val request_change = findViewById<AppCompatButton>(R.id.click_verify)
        request_change.setOnClickListener {
            startActivity(Intent(this, UpdatePasswordActivity::class.java))
        }
    }
}