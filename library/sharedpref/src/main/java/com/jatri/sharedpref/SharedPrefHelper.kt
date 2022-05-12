package com.jatri.sharedpref

import android.content.Context

class SharedPrefHelper(application: Context){
    private var sharedPreferences = application.getSharedPreferences("com.jatri.jatri.user.v.0",0)
    fun putString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String): String{
        return sharedPreferences.getString(key, "")!!
    }

    fun putBool(key: String, value: Boolean){
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun putInt(key: String, value: Int){
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, -1)
    }

    fun clearAllCache() {
        sharedPreferences.edit().clear().apply()
    }
}