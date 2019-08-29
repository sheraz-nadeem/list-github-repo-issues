package com.sheraz.core.network

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

class HttpLogger: HttpLoggingInterceptor.Logger {

    override fun log(message: String) {
        Log.d(TAG, "RAW_MESSAGE $message")
    }


    companion object {
        private val TAG = HttpLogger::class.java.simpleName
    }
}