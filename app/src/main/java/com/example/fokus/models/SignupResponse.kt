package com.example.fokus.models

data class SignupResponse(
    val token: String,
    val message: String,
    val data: User?
)