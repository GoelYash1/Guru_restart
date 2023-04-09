package com.axyz.upasthithguru.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.app

import io.realm.kotlin.mongodb.User
import okhttp3.internal.wait

//import com.axyz.upasthithguru.databinding.ActivityMainBinding


class EntryActivity : AppCompatActivity() {
    //    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        val btn_register = findViewById<Button>(R.id.btn_register)
        btn_register.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        val btn_login = findViewById<Button>(R.id.btn_login)
        btn_login.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
    }
}