package com.axyz.upasthithguru.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.activity.LoginActivity
import com.axyz.upasthithguru.app
import com.axyz.upasthithguru.data.realmModule.realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch

class Setting : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var signout_Btn = view?.findViewById<RelativeLayout>(R.id.signout_button)
//            signout_Btn.se
        signout_Btn?.setOnClickListener {
            CoroutineScope(coroutineContext).launch {
                realm.close()
                app.currentUser?.logOut()
                Log.d("User Logged Out", "User Logged Out")
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }

        CoroutineScope(coroutineContext).launch {
            app.currentUser?.logOut()
            Log.d("User Logged Out", "User Logged Out")
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)

    }
}