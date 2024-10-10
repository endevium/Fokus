package com.example.fokus.api

import android.content.Context
import android.content.SharedPreferences

fun getToken(context: Context): String? {
    val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    return sharedPref.getString("token", null)
}