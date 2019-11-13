package com.sheraz.listgithubrepoissues.ui.modules.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.sheraz.core.utils.Logger
import com.sheraz.listgithubrepoissues.di.Injector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren

/**
 * An abstract base class for our ViewModels
 */

abstract class BaseViewModel: ViewModel() {


    private val isLoading = ObservableBoolean(false)
    private val parentJob = Job()
    protected val logger = Logger()
    protected val dispatcherProvider = Injector.getCoreComponent().coroutinesDispatcherProvider()
    protected val scope = CoroutineScope(dispatcherProvider.mainDispatcher + parentJob)

    init {
        logger.d(TAG, "init(): ")
    }


    fun getIsLoading(): ObservableBoolean {
        return isLoading
    }

    fun setIsLoading(loading: Boolean) {
        isLoading.set(loading)
    }

    /**
     * This method is special as it cancels all the coroutines
     * running under as child to our `parentJob` as soon as
     * the view model is detached from its lifecycle owner.
     */
    override fun onCleared() {
        logger.d(TAG, "onCleared(): ")
        super.onCleared()
        parentJob.cancelChildren()
    }

    companion object {
        private val TAG = BaseViewModel::class.java.simpleName
    }
}