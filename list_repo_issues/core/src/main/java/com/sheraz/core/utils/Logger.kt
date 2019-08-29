package com.sheraz.core.utils

import android.util.Log
import com.sheraz.core.BuildConfig

open class Logger {

    private val TAG = Logger::class.java.simpleName
    private var loggingEnabled = false

    init {
        if (BuildConfig.DEBUG) {
            loggingEnabled = true
        }
        Log.d(TAG, "init: loggingEnabled: $loggingEnabled")
    }


    /**
     * Debug Log Level
     */
    fun d(s: String, message: String) = let { if (loggingEnabled) Log.d(s, message) }


    /**
     * Info Log Level
     */
    fun i(s: String, message: String) = let { if (loggingEnabled) Log.i(s, message) }


    /**
     * Verbose Log Level
     */
    fun v(s: String, message: String) = let { if (loggingEnabled) Log.v(s, message) }


    /**
     * Warning Log Level
     */
    fun w(s: String, message: String) = let { if (loggingEnabled) Log.w(s, message) }


    /**
     * Error Log Level
     */
    fun e(s: String, message: String) = let { if (loggingEnabled) Log.e(s, message) }


}