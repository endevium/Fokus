package com.example.fokus.models

data class TaskHistory(
    val task_id: Int,
    val user_id: Int,
    val status: String,
    val description: String
)
