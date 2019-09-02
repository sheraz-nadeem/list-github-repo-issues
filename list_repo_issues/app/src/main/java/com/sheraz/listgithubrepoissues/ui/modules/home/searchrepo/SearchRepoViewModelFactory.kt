package com.sheraz.listgithubrepoissues.ui.modules.home.searchrepo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.core.utils.Logger

/**
 * Factory for creating [SearchRepoViewModel].
 */

class SearchRepoViewModelFactory(
    private val logger: Logger,
    private val appRepository: AppRepository
): ViewModelProvider.Factory {

    init {
        logger.d(TAG, "init(): ")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        logger.d(TAG, "create(): ")

        if (modelClass != SearchRepoViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        return SearchRepoViewModel(appRepository) as T

    }

    companion object {
        private val TAG = SearchRepoViewModelFactory::class.java.simpleName
    }
}