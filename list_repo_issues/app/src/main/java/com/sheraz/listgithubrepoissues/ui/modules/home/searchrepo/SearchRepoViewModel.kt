package com.sheraz.listgithubrepoissues.ui.modules.home.searchrepo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.listgithubrepoissues.extensions.toUiModel
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoItem
import com.sheraz.listgithubrepoissues.ui.modules.base.BaseBoundaryCallback
import com.sheraz.listgithubrepoissues.ui.modules.base.BaseViewModel
import kotlinx.coroutines.async
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

    fun search(query: String, pageSize: Int = AppRepository.NETWORK_PAGE_SIZE, page: Int = 1) {
        scope.launch(dispatcherProvider.ioDispatcher) {
            lastSearchQuery = query
            appRepository.loadGitHubReposList(query, pageSize, page)
        }
    }

    fun onRefresh(query: String, pageSize: Int = AppRepository.NETWORK_PAGE_SIZE, page: Int = -1) =
        scope.launch(dispatcherProvider.ioDispatcher) {
            appRepository.loadGitHubReposList(query, pageSize, page)
        }

    fun onClearCache() = scope.launch(dispatcherProvider.ioDispatcher) {
        async { appRepository.clearRepoIssuesCache() }.await()
        async { appRepository.clearReposCache() }.await()
    }

    fun buildLivePagedList() {
        pagedListLiveData = LivePagedListBuilder(_allReposPagedFactory, pagedListConfig)
            .setBoundaryCallback(GitHubRepoBoundaryCallback())
            .build()
    }

    override fun onCleared() {

        logger.d(TAG, "onCleared(): ")
        super.onCleared()

    }

    inner class GitHubRepoBoundaryCallback: BaseBoundaryCallback<GitHubRepoItem>(logger, appRepository) {

        override fun requestData(isLastItem: Boolean) = when (appRepository.isFetchInProgress.value!!) {

            true -> logger.v(TAG_REPO_BOUNDARY_CALLBACK, "requestData(): isLastItem = $isLastItem, NETWORK FETCH is already in progress")
            false -> {
                logger.d(TAG_REPO_BOUNDARY_CALLBACK, "requestData(): isLastItem = $isLastItem, lastSearchQuery: $lastSearchQuery")
                search(lastSearchQuery)
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