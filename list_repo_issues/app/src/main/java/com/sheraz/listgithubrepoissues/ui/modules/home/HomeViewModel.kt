package com.sheraz.listgithubrepoissues.ui.modules.home

import com.sheraz.listgithubrepoissues.ui.modules.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope

class HomeViewModel: BaseViewModel() {

    private val scope = CoroutineScope(dispatcherProvider.mainDispatcher + parentJob)


    init {
        logger.d(TAG, "init(): ")
        setIsLoading(false)
    }

    override fun onCleared() {

        logger.d(TAG, "onCleared(): ")
        super.onCleared()

    }

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }

}