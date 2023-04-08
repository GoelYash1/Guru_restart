package com.axyz.upasthithguru.apidata

data class SignupRequest(
    val name: String,
    val email: String,
    val password: String,
    val description: String,
)

data class LoginRequest(
    val email: String,
    val password: String,
)