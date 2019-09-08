package com.sheraz.listgithubrepoissues.ui.modules.home.searchrepo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.core.data.sharedprefs.AppSharedPrefs
import com.sheraz.core.data.sharedprefs.getSearchQuery
import com.sheraz.listgithubrepoissues.extensions.toUiModel
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoItem
import com.sheraz.listgithubrepoissues.ui.modules.base.BaseBoundaryCallback
import com.sheraz.listgithubrepoissues.ui.modules.base.BaseViewModel
import com.sheraz.listgithubrepoissues.ui.modules.home.HomeViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


/**
 * ViewModel that is used by our [SearchRepositoryBottomSheetDialogFragment]
 * to maintain its state.
 */

class SearchRepoViewModel(
    private val appRepository: AppRepository,
    private val appSharedPrefs: AppSharedPrefs
): BaseViewModel() {

    private val pagedListConfig: PagedList.Config
    private val _allReposPagedFactory = appRepository.getAllReposPagedFactory().map { it.toUiModel() }

    private lateinit var liveDataPagedList: LiveData<PagedList<GitHubRepoItem>>

    val networkFetchStatusLiveData = appRepository.isFetchInProgress
    val networkErrorStatusLiveData = appRepository.networkError

    var lastSearchQuery = appSharedPrefs.getSearchQuery()

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

    fun search(doResetNoMoreItemsAvailable: Boolean = false, pageSize: Int = AppRepository.NETWORK_PAGE_SIZE, page: Int = 1) {
        scope.launch(dispatcherProvider.ioDispatcher) {
            lastSearchQuery = appSharedPrefs.getSearchQuery()
            logger.i(TAG, "search(): lastSearchQuery: $lastSearchQuery")
            appRepository.loadGitHubReposList(doResetNoMoreItemsAvailable, lastSearchQuery, pageSize, page)
        }
    }

    fun buildLivePagedList() {
        liveDataPagedList = LivePagedListBuilder(_allReposPagedFactory, pagedListConfig)
            .setBoundaryCallback(GitHubRepoBoundaryCallback())
            .build()
    }

    fun getLiveDataPagedList() = liveDataPagedList

    fun onRefresh() = liveDataPagedList.value?.dataSource?.invalidate()

    fun onClearReposCache() = scope.launch(dispatcherProvider.ioDispatcher) { appRepository.clearReposCache() }

    fun onClearCache() = scope.launch(dispatcherProvider.ioDispatcher) {
        async { appRepository.clearRepoIssuesCache() }.await()
        async { appRepository.clearReposCache() }.await()
    }


    override fun onCleared() {

        logger.d(TAG, "onCleared(): ")
        super.onCleared()

    }

    inner class GitHubRepoBoundaryCallback: BaseBoundaryCallback<GitHubRepoItem>(logger, appRepository) {

        override fun requestData(isLastItem: Boolean, doResetNoMoreItemsAvailable: Boolean) = when (appRepository.isFetchInProgress.value!!) {

            true -> logger.v(TAG_REPO_BOUNDARY_CALLBACK, "requestData(): " +
                    "isLastItem = $isLastItem, doResetNoMoreItemsAvailable = $doResetNoMoreItemsAvailable, NETWORK FETCH is already in progress")
            false -> {
                logger.d(TAG_REPO_BOUNDARY_CALLBACK, "requestData(): " +
                        "isLastItem = $isLastItem, doResetNoMoreItemsAvailable = $doResetNoMoreItemsAvailable, lastSearchQuery: $lastSearchQuery")
                search(doResetNoMoreItemsAvailable)
            }
        }

    }

    companion object {
        private val TAG = SearchRepoViewModel::class.java.simpleName
        private val TAG_REPO_BOUNDARY_CALLBACK: String = GitHubRepoBoundaryCallback::class.java.simpleName

        const val DATABASE_PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 5
    }

}