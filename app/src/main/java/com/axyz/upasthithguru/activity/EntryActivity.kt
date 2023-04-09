package com.axyz.upasthithguru.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.app
import com.axyz.upasthithguru.databinding.ActivityEntryBinding

import io.realm.kotlin.mongodb.User
import okhttp3.internal.wait

//import com.axyz.upasthithguru.databinding.ActivityMainBinding


class EntryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEntryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEntryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val btn_new_journey = findViewById<Button>(R.id.btn_new_journey)
        btn_new_journey.setOnClickListener {
            navigateToRegisterActivity()
        }

        val btn_continue_journey = findViewById<Button>(R.id.btn_continue_journey)
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