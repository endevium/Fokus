package com.example.fokus.api

import android.content.Context
import android.content.SharedPreferences

fun saveLongbreak(context: Context, minutes: Long, seconds: Long, firstDigit: String,
                  secondDigit: String, thirdDigit: String, fourthDigit: String) {
    val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putLong("longbreak_minutes", minutes)
    editor.putLong("longbreak_seconds", seconds)

    editor.putString("firstDigitLB", firstDigit)
    editor.putString("secondDigitLB", secondDigit)
    editor.putString("thirdDigitLB", thirdDigit)
    editor.putString("fourthDigitLB", fourthDigit)

    editor.apply()
}