package com.axyz.upasthithguru.apidata

data class ResponseObj(
    val success : String,
    val message: String = "NAN",
    val authToken:String,
)