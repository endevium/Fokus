package com.example.fokus.api

import android.content.Context
import android.content.SharedPreferences

fun pomodoroMinutes(context: Context): Long? {
    val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    return sharedPref.getLong("pomodoro_minutes", 25)
}