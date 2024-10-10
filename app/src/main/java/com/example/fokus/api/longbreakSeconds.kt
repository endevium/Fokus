package com.example.fokus.api

import android.content.Context
import android.content.SharedPreferences

fun longbreakSeconds(context: Context): Long? {
    val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    return sharedPref.getLong("longbreak_seconds", 0)
}