package com.sheraz.listgithubrepoissues.ui.modules.home.searchrepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.core.data.sharedprefs.AppSharedPrefs
import com.sheraz.core.data.sharedprefs.getSearchQuery
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
    private val appRepository: AppRepository,
    private val appSharedPrefs: AppSharedPrefs
): BaseViewModel() {


    private val _queryLiveData = MutableLiveData<String>().apply { postValue(appSharedPrefs.getSearchQuery()) }
    private val _reposLiveData: LiveData<DataSource.Factory<Int, GitHubRepoItem>> = Transformations.map(_queryLiveData) {query ->
        logger.v(TAG, "_reposLiveData: query = $query")
        appRepository.getAllReposPagedFactory(query).map {it.toUiModel()}.also { search() }
    }
    private val _liveDataPagedList: LiveData<PagedList<GitHubRepoItem>> = Transformations.switchMap(_reposLiveData) {
        logger.v(TAG, "_liveDataPagedList: Building livePagedList")
        LivePagedListBuilder(it, pagedListConfig)
            .setBoundaryCallback(GitHubRepoBoundaryCallback())
            .build()
    }
    val liveDataPagedList: LiveData<PagedList<GitHubRepoItem>>
        get() = _liveDataPagedList

    val networkFetchStatusLiveData = appRepository.isFetchInProgress
    val networkErrorStatusLiveData = appRepository.networkError

    private val pagedListConfig: PagedList.Config

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
    }

    fun setSearchQuery(query: String) {
        logger.i(TAG, "setSearchQuery(): query: $query")
        _queryLiveData.postValue(query)
    }

    fun search() {
        scope.launch(dispatcherProvider.ioDispatcher) {
            logger.i(TAG, "search(): _queryLiveData.value: ${_queryLiveData.value!!}")
            appRepository.loadGitHubReposList(_queryLiveData.value!!)
        }
    }

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

        override fun requestData(isLastItem: Boolean) = when (appRepository.isFetchInProgress.value!!) {

            true -> logger.v(TAG_REPO_BOUNDARY_CALLBACK, "requestData(): " +
                    "isLastItem = $isLastItem, NETWORK FETCH is already in progress")
            false -> {
                logger.d(TAG_REPO_BOUNDARY_CALLBACK, "requestData(): " +
                        "isLastItem = $isLastItem, lastSearchQuery: ${_queryLiveData.value!!}")
                search()
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