package com.sheraz.core.data.sharedprefs

import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Extension functions for [SharedPreferences] class
 */

fun SharedPreferences.saveInt(valueKey: String, value: Int) = edit().putInt(valueKey, value).apply()
fun SharedPreferences.saveString(valueKey: String, value: String) = edit().putString(valueKey, value).apply()
fun SharedPreferences.saveBoolean(valueKey: String, value: Boolean) = edit().putBoolean(valueKey, value).apply()
fun SharedPreferences.saveLong(valueKey: String, value: Long) = edit().putLong(valueKey, value).apply()
fun SharedPreferences.saveFloat(valueKey: String, value: Float) = edit().putFloat(valueKey, value).apply()
fun SharedPreferences.clearPrefs() = edit().clear().apply()


class AppSharedPrefs @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun clearPrefsCache(): Unit = sharedPreferences.clearPrefs()

    fun get(valueKey: String, valueDefault: String): String? = sharedPreferences.getString(valueKey, valueDefault)

    fun set(valueKey: String, value: String): Unit = sharedPreferences.saveString(valueKey, value)

    fun get(valueKey: String, valueDefault: Int): Int = sharedPreferences.getInt(valueKey, valueDefault)

    fun set(valueKey: String, value: Int): Unit = sharedPreferences.saveInt(valueKey, value)

    fun get(valueKey: String, valueDefault: Boolean): Boolean = sharedPreferences.getBoolean(valueKey, valueDefault)

    fun set(valueKey: String, value: Boolean): Unit = sharedPreferences.saveBoolean(valueKey, value)

    fun get(valueKey: String, valueDefault: Long): Long = sharedPreferences.getLong(valueKey, valueDefault)

    fun set(valueKey: String, value: Long): Unit = sharedPreferences.saveLong(valueKey, value)

    fun get(valueKey: String, valueDefault: Float): Float = sharedPreferences.getFloat(valueKey, valueDefault)

    fun set(valueKey: String, value: Float): Unit = sharedPreferences.saveFloat(valueKey, value)


    companion object {
        @Volatile
        private var INSTANCE: AppSharedPrefs? = null

        fun getInstance(sharedPreferences: SharedPreferences): AppSharedPrefs {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppSharedPrefs(sharedPreferences).also {
                    INSTANCE = it
                }
            }
        }
    }
}
