package com.example.fokus.models

import retrofit2.Response

data class NotesResponse(
    val message: String,
    val notes: Notes?
)
