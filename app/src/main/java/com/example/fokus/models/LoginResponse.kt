package com.example.fokus.models

data class LoginResponse(
    val token: String,
    val message: String,
    val user: User?
)