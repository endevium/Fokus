package com.example.fokus.api

import android.content.Context
import android.content.SharedPreferences

fun saveToken(context: Context, token: String) {
    val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    val sharedPrefEditor = sharedPref.edit()
    sharedPrefEditor.putString("token", token)
    sharedPrefEditor.apply()
}