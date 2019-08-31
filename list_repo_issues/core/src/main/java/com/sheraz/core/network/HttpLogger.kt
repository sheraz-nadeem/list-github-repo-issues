package com.sheraz.core.network

import com.sheraz.core.utils.Logger
import okhttp3.logging.HttpLoggingInterceptor

/**
 * This class is responsible for logging our network
 * requests. It is used by [HttpLoggingInterceptor]
 * to log network requests & responses.
 */

class HttpLogger(
    private val logger: Logger
): HttpLoggingInterceptor.Logger {

    override fun log(message: String) {
        logger.d(TAG, "RAW_MESSAGE $message")
    }


    companion object {
        private val TAG = HttpLogger::class.java.simpleName
    }
}