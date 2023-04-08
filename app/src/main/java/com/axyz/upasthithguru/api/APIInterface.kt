package com.axyz.upasthithguru.api

import android.content.Context
import com.axyz.upasthithguru.apidata.LoginRequest
import com.axyz.upasthithguru.apidata.ResponseObj
import com.axyz.upasthithguru.apidata.SignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface APIInterface {
    @POST("teacher/createTeacher")
    suspend fun signup(@Body signupRequest: SignupRequest): Response<ResponseObj>

    @POST("teacher/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ResponseObj>

//    @POST("/session/attend")
//    suspend fun postAttendance(
//        @Header("Cookie") token: String,
//        @Body sessionDetails: QRData
//    ): Response<Boolean>

}