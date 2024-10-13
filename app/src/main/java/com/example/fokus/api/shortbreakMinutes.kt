package com.example.fokus.api

import android.content.Context
import android.content.SharedPreferences

fun shortbreakMinutes(context: Context): Long? {
    val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    return sharedPref.getLong("shortbreak_minutes", 5)
}