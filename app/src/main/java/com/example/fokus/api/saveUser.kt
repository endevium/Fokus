package com.example.fokus.api

import android.content.Context
import android.content.SharedPreferences

class saveUser {
    fun saveId(context: Context, id: Int) {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val sharedPrefEditor = sharedPref.edit()
        sharedPrefEditor.putInt("id", id)
        sharedPrefEditor.apply()
    }

    fun saveUsername(context: Context, username: String) {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val sharedPrefEditor = sharedPref.edit()
        sharedPrefEditor.putString("username", username)
        sharedPrefEditor.apply()
    }

    fun saveEmail(context: Context, email: String) {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val sharedPrefEditor = sharedPref.edit()
        sharedPrefEditor.putString("email", email)
        sharedPrefEditor.apply()
    }

    fun savePass(context: Context, password: String) {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val sharedPrefEditor = sharedPref.edit()
        sharedPrefEditor.putString("password", password)
        sharedPrefEditor.apply()
    }

    fun getId(context: Context): Int? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getInt("id", -1)
    }

    fun getUsername(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("username", null)
    }

    fun getEmail(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("email", null)
    }

    fun getPassword(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("password", null)
    }
}