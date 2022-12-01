package com.example.androidcourseproject.utilClasses

import android.content.Context
import android.content.SharedPreferences

class ActivitySharedPreferences (private val context: Context) {
    private val prefsName = "scoreCounter"
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    fun saveIntByKey(keyName: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(keyName, value)
        editor.apply()
    }

    fun getIntByKey(keyName: String): Int {
        return sharedPreferences.getInt(keyName, 0)
    }
}