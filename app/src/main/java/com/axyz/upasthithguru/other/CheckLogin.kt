package com.axyz.upasthithguru.other

import android.content.Context

fun CheckLogin(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("upasthithGuruu", Context.MODE_PRIVATE)
    val authToken = sharedPreferences.getString("authToken", "")
    println("authToken :: $authToken")
    return !authToken.isNullOrEmpty()
}
