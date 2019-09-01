package com.sheraz.core.utils

import com.sheraz.core.network.response.Result
import java.io.IOException

/**
 * A utility method that provides a safe way to perform network IO
 * and catch/return Exception in case a network call fails
 */

suspend fun <T : Any> safeApiCall(networkBlock: suspend () -> Result<T>, errorMessage: String): Result<T> {
    return try {
        networkBlock()
    } catch (e: Exception) {
        // An exception was thrown when calling the API so we're converting this to an IOException
        Result.Error(IOException(errorMessage, e))
    }
}