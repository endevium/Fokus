package com.example.fokus.api

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class saveSettings {
    fun saveTheme(context: Context, theme: String) {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val sharedPrefEditor = sharedPref.edit()
        sharedPrefEditor.putString("theme", theme)
        sharedPrefEditor.apply()
    }

    fun saveSilent(context: Context, state: Boolean) {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val sharedPrefEditor = sharedPref.edit()
        sharedPrefEditor.putBoolean("silent_mode", state)
        sharedPrefEditor.apply()
    }

    fun saveVibration(context: Context, state: Boolean) {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val sharedPrefEditor = sharedPref.edit()
        sharedPrefEditor.putBoolean("vibration", state)
        sharedPrefEditor.apply()
    }

    fun saveVolume(context: Context, volume: Int) {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val sharedPrefEditor = sharedPref.edit()
        sharedPrefEditor.putInt("volume", volume)
        sharedPrefEditor.apply()
    }

    fun getTheme(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("theme", "default")
    }

    fun getSilent(context: Context): Boolean? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("silent_mode", false)
    }

    fun getVibration(context: Context): Boolean? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("vibration", true)

    }

    fun getVolume(context: Context): Int? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getInt("volume", 100)
    }
}