package com.sheraz.listgithubrepoissues.ui.modules.home

import android.graphics.pdf.PdfRenderer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.listgithubrepoissues.extensions.toUiModel
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoIssueItem
import com.sheraz.listgithubrepoissues.ui.modules.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * ViewModel that is used by our HomeActivity
 * to maintain its state.
 */

class HomeViewModel(
    private val appRepository: AppRepository
): BaseViewModel() {

    private val pagedListConfig: PagedList.Config
    private val _allReposPagedFactory = appRepository.getAllReposPagedFactory().map { it.toUiModel() }

    val networkFetchStatusLiveData = appRepository.isFetchInProgress
    val networkErrorStatusLiveData = appRepository.networkError

    var pagedListLiveData: LiveData<PagedList<GitHubRepoIssueItem>>? = null

    init {

        logger.d(TAG, "init(): ")
        setIsLoading(false)

        // Kahaf
        pagedListConfig =
            PagedList.Config.Builder()
                .setPrefetchDistance(PREFETCH_DISTANCE)
                .setPageSize(DATABASE_PAGE_SIZE)
                .setInitialLoadSizeHint(DATABASE_PAGE_SIZE)
                .setEnablePlaceholders(false)
                .build()

        buildLivePagedList()
    }

    fun loadData(ownerName: String, repoName: String, pageSize: Int = AppRepository.NETWORK_PAGE_SIZE, page: Int = 1) =
        scope.launch(dispatcherProvider.ioDispatcher) {
            appRepository.loadGitHubRepoIssuesList(ownerName, repoName, pageSize, page)
        }

    fun onRefresh(ownerName: String, repoName: String, pageSize: Int = AppRepository.NETWORK_PAGE_SIZE, page: Int = -1) =
        scope.launch(dispatcherProvider.ioDispatcher) {
            appRepository.loadGitHubRepoIssuesList(ownerName, repoName, pageSize, page)
        }

    fun onClearCache() = scope.launch(dispatcherProvider.ioDispatcher) {
        appRepository.clearCache()
    }

    fun buildLivePagedList() {
        pagedListLiveData = LivePagedListBuilder(_allReposPagedFactory, pagedListConfig)
            .setBoundaryCallback(RepoBoundaryCallback())
            .build()
    }

    override fun onCleared() {

        logger.d(TAG, "onCleared(): ")
        super.onCleared()

    }

    inner class RepoBoundaryCallback : PagedList.BoundaryCallback<GitHubRepoIssueItem>() {

        /**
         * Database returned 0 items. We should query the backend for more items.
         */
        override fun onZeroItemsLoaded() {
            logger.d(TAG_REPO_BOUNDARY_CALLBACK, "onZeroItemsLoaded(): ")
            requestAndSaveData()
        }

        /**
         * When all items in the database were loaded, we need to query the backend for more items.
         */
        override fun onItemAtEndLoaded(itemAtEnd: GitHubRepoIssueItem) {
            logger.d(TAG_REPO_BOUNDARY_CALLBACK, "onItemAtEndLoaded(): ")
            if (appRepository.noMoreItemsAvailable.value!!) return
            requestAndSaveData()
        }

        private fun requestAndSaveData() {

            if (appRepository.isFetchInProgress.value!!) return
            loadData("tensorflow", "ecosystem")

        }
    }

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
        private val TAG_REPO_BOUNDARY_CALLBACK: String = RepoBoundaryCallback::class.java.simpleName

        const val DATABASE_PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 5
    }

}