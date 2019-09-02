package com.sheraz.listgithubrepoissues.ui.modules.home.searchrepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.listgithubrepoissues.extensions.toUiModel
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoItem
import com.sheraz.listgithubrepoissues.ui.modules.base.BaseViewModel
import kotlinx.coroutines.launch


/**
 * ViewModel that is used by our [SearchRepositoryBottomSheetDialogFragment]
 * to maintain its state.
 */

class SearchRepoViewModel(
    private val appRepository: AppRepository
): BaseViewModel() {

    private val pagedListConfig: PagedList.Config
    private val _allReposPagedFactory = appRepository.getAllReposPagedFactory().map { it.toUiModel() }

    val networkFetchStatusLiveData = appRepository.isFetchInProgress
    val networkErrorStatusLiveData = appRepository.networkError

    var pagedListLiveData: LiveData<PagedList<GitHubRepoItem>>? = null
    var lastSearchQuery = ""

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

    fun search(query: String, pageSize: Int = AppRepository.NETWORK_PAGE_SIZE, page: Int = 1) =
        scope.launch(dispatcherProvider.ioDispatcher) {
            lastSearchQuery = query
            appRepository.loadGitHubReposList(query, pageSize, page)
        }

    fun onRefresh(query: String, pageSize: Int = AppRepository.NETWORK_PAGE_SIZE, page: Int = -1) =
        scope.launch(dispatcherProvider.ioDispatcher) {
            appRepository.loadGitHubReposList(query, pageSize, page)
        }

    fun onClearCache() = scope.launch(dispatcherProvider.ioDispatcher) {
        appRepository.clearReposCache()
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

    inner class RepoBoundaryCallback : PagedList.BoundaryCallback<GitHubRepoItem>() {

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
        override fun onItemAtEndLoaded(itemAtEnd: GitHubRepoItem) {
            logger.d(TAG_REPO_BOUNDARY_CALLBACK, "onItemAtEndLoaded(): ")
            if (appRepository.noMoreItemsAvailable.value!!) return
            requestAndSaveData()
        }

        private fun requestAndSaveData() {

            if (appRepository.isFetchInProgress.value!!) return

            logger.d(TAG_REPO_BOUNDARY_CALLBACK, "onItemAtEndLoaded(): lastSearchQuery: $lastSearchQuery")
            search(lastSearchQuery)

        }
    }

    companion object {
        private val TAG = SearchRepoViewModel::class.java.simpleName
        private val TAG_REPO_BOUNDARY_CALLBACK: String = RepoBoundaryCallback::class.java.simpleName

        const val DATABASE_PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 5
    }

}