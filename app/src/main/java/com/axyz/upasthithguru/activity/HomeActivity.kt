package com.axyz.upasthithguru.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.fragments.Attendance
import com.axyz.upasthithguru.fragments.Home
import com.axyz.upasthithguru.fragments.Setting
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val homeFragment = Home();
        val attendanceFragment = Attendance();
        val settingsFragment = Setting();
        makeCurrentFragment(homeFragment);
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.ic_home ->makeCurrentFragment(homeFragment)
                R.id.ic_attendance -> makeCurrentFragment(attendanceFragment)
                R.id.ic_settings -> makeCurrentFragment(settingsFragment)
            }
            true
        }


    }
    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
    override fun onBackPressed() {
        // Do nothing
    }
}