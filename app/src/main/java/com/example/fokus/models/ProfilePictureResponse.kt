package com.example.fokus.models

data class ProfilePictureResponse(
    val success: Boolean,
    val message: String,
    val profile_picture_url: String?
)
