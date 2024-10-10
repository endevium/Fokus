package com.example.fokus.api

import android.content.Context
import android.content.SharedPreferences

fun pomodoroSeconds(context: Context): Long? {
    val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    return sharedPref.getLong("pomodoro_seconds", 0)
}