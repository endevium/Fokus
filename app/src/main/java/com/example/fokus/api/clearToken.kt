package com.example.fokus.api

import android.content.Context
import android.content.SharedPreferences

fun clearToken(context: Context) {
    val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    val sharedPrefEditor = sharedPref.edit()
    sharedPrefEditor.clear()
    sharedPrefEditor.apply()
}