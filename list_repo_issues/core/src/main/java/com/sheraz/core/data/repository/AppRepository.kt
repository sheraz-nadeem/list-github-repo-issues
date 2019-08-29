package com.sheraz.core.data.repository

import androidx.lifecycle.LiveData

interface AppRepository {

    val isFetchInProgress: LiveData<Boolean>
    val networkError: LiveData<Exception>

}