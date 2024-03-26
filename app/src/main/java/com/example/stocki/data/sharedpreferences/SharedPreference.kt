package com.example.stocki.data.sharedpreferences

import javax.inject.Singleton

interface SharedPreference {

    fun addString(key: String, value: String)
    fun addFloat(key: String, value: Float)
    fun getFloat(key: String, value: Float) :Float
    fun getString(key: String, defaultValue: String): String
    fun addBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun deleteData(key: String)

}