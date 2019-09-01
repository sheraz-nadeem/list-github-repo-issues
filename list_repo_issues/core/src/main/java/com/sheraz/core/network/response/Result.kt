package com.sheraz.core.network.response

/**
 * The key benefit of making this class as 'sealed' is to use
 * it in a 'when' expression (means 'when' used to return a value),
 * as it's becomes clear to the compiler that 'when' statement
 * covers all cases, you don't need to add an else clause to
 * 'when' statement
 *
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}