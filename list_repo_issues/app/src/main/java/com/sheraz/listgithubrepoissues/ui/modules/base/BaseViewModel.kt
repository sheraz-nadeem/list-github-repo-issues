package com.sheraz.listgithubrepoissues.ui.modules.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.sheraz.core.data.CoroutinesDispatcherProvider
import com.sheraz.core.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import javax.inject.Inject

/**
 * An abstract base class for our ViewModels
 */

abstract class BaseViewModel(
    private val logger: Logger,
    dispatcherProvider: CoroutinesDispatcherProvider
): ViewModel() {

    private val isLoading = ObservableBoolean(false)
    private val parentJob = Job()
    protected val scope = CoroutineScope(dispatcherProvider.mainDispatcher + parentJob)

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