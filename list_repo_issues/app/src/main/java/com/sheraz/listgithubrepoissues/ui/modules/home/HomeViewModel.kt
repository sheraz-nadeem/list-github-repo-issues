package com.sheraz.listgithubrepoissues.ui.modules.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.listgithubrepoissues.extensions.toUiModel
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoIssueItem
import com.sheraz.listgithubrepoissues.ui.modules.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.net.URLEncoder

/**
 * ViewModel that is used by our HomeActivity
 * to maintain its state.
 */

class HomeViewModel(
    private val appRepository: AppRepository
): BaseViewModel() {

    private val _gitHubRepoIssuesList = appRepository.getAllRepoIssuesLiveData()

    init {
        logger.d(TAG, "init(): ")
        setIsLoading(false)
    }

    fun loadData(ownerName: String, repoName: String, pageSize: Int = AppRepository.NETWORK_PAGE_SIZE, page: Int = 1) =
        scope.launch(dispatcherProvider.ioDispatcher) {
            appRepository.loadGitHubRepoIssuesList(ownerName, repoName, pageSize, page)
        }

    fun getGitHubRepoIssueItemsList(): LiveData<List<GitHubRepoIssueItem>> =
        Transformations.map(_gitHubRepoIssuesList) {entityList ->
        entityList.map { it.toUiModel() }
    }

    fun getLoadingLiveData(): LiveData<Boolean> = appRepository.isFetchInProgress

    fun getNetworkErrorLiveData(): LiveData<Exception> = appRepository.networkError

    fun onRefresh() = scope.launch(dispatcherProvider.ioDispatcher) { appRepository.refreshGitHubRepoIssuesList() }

    fun onClearCache() = scope.launch(dispatcherProvider.ioDispatcher) {  appRepository.clearCache() }

    override fun onCleared() {

        logger.d(TAG, "onCleared(): ")
        super.onCleared()

    }

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }

}