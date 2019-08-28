package com.sheraz.listgithubrepoissues.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sheraz.listgithubrepoissues.utils.Logger
import kotlin.Exception

class AppRepositoryImpl: AppRepository {

    private val logger = Logger()

    private val _isFetchInProgress = MutableLiveData<Boolean>().apply { postValue(false) }
    override val isFetchInProgress: LiveData<Boolean>
        get() = _isFetchInProgress

    private val _networkError = MutableLiveData<Exception>()
    override val networkError: LiveData<Exception>
        get() = _networkError


    init {
        logger.d(TAG, "init(): ")
    }


    companion object {
        private val TAG: String = AppRepositoryImpl::class.java.simpleName
    }
}