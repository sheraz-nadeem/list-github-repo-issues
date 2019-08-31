package com.sheraz.listgithubrepoissues.ui.modules.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.core.utils.Logger

/**
 * Factory for creating [HomeViewModel].
 */

class HomeViewModelFactory(
    private val logger: Logger,
    private val appRepository: AppRepository
): ViewModelProvider.Factory {

    init {
        logger.d(TAG, "init(): ")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        logger.d(TAG, "create(): ")

        if (modelClass != HomeViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        return HomeViewModel(appRepository) as T

    }

    companion object {
        private val TAG = HomeViewModelFactory::class.java.simpleName
    }
}