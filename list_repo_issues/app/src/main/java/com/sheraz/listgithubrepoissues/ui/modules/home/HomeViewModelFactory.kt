package com.sheraz.listgithubrepoissues.ui.modules.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sheraz.core.data.repository.AppRepository

/**
 * Factory for creating [HomeViewModel].
 */

class HomeViewModelFactory(
    private val appRepository: AppRepository
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass != HomeViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        return HomeViewModel(appRepository) as T
    }
}