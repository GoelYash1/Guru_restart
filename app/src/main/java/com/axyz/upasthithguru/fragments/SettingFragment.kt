package com.axyz.upasthithguru.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.activity.LoginActivity
import com.axyz.upasthithguru.app
import com.axyz.upasthithguru.data.realmModule.realm
import kotlinx.coroutines.runBlocking

class Setting : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        val signout_Btn = view?.findViewById<RelativeLayout>(R.id.signout_button)
        signout_Btn?.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireContext())
            dialogBuilder.setMessage("Are You Sure?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, _ ->
                    val progressBar = view?.findViewById<ProgressBar>(R.id.progress_bar)
                    progressBar?.visibility = View.VISIBLE
                    progressBar?.bringToFront()
                    runBlocking{
                        realm.close()
                        app.currentUser?.logOut()
                        Log.d("User Logged Out", "User Logged Out")
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
            val alert = dialogBuilder.create()
            alert.setTitle("Logout")

            // Set the "Yes" button color to green
            alert.setOnShowListener {
                alert.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(resources.getColor(R.color.custom_color_bg))
            }

            // Set the "No" button color to red
            alert.setOnShowListener {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(resources.getColor(R.color.Res))
            }

            alert.show()
        }

        // Inflate the layout for this fragment
        return view
    }
}