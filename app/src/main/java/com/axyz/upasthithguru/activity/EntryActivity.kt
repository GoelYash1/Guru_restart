package com.axyz.upasthithguru.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.databinding.ActivityEntryBinding

//import com.axyz.upasthithguru.databinding.ActivityMainBinding


class EntryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEntryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEntryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val btn_new_journey = findViewById<AppCompatButton>(R.id.btn_new_journey)
        btn_new_journey.setOnClickListener {
            navigateToRegisterActivity()
        }

        val btn_continue_journey = findViewById<AppCompatButton>(R.id.btn_continue_journey)
        btn_continue_journey.setOnClickListener {
            navigateToLoginActivity()
        }
    }

    private fun navigateToLoginActivity(){
        startActivity(Intent(applicationContext, LoginActivity::class.java))
    }
    private fun navigateToRegisterActivity(){
        startActivity(Intent(applicationContext, RegisterActivity::class.java))
    }
}