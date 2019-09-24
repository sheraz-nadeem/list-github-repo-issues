package com.sheraz.listgithubrepoissues.ui.modules.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.core.data.sharedprefs.AppSharedPrefs
import com.sheraz.core.data.sharedprefs.getGitHubRepoName
import com.sheraz.core.data.sharedprefs.getGitHubRepoOwner
import com.sheraz.listgithubrepoissues.extensions.toUiModel
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoIssueItem
import com.sheraz.listgithubrepoissues.ui.modules.base.BaseBoundaryCallback
import com.sheraz.listgithubrepoissues.ui.modules.base.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * ViewModel that is used by our HomeActivity
 * to maintain its state.
 */

class HomeViewModel(
    private val appRepository: AppRepository,
    private val appSharedPrefs: AppSharedPrefs
): BaseViewModel() {

    private val _selectedRepoNameLiveData = MutableLiveData<String>().apply { postValue(appSharedPrefs.getGitHubRepoName()) }
    private val _selectedRepoLiveData: LiveData<DataSource.Factory<Int, GitHubRepoIssueItem>> = Transformations.map(_selectedRepoNameLiveData) {it ->
        val ownerName = appSharedPrefs.getGitHubRepoOwner()
        val repoName = appSharedPrefs.getGitHubRepoName()
        logger.v(TAG, "_selectedRepoLiveData(): ownerName: $ownerName, repoName: $repoName")
        appRepository.getAllRepoIssuesPagedFactory(ownerName, repoName).map{ it.toUiModel() }.also { loadData() }
    }
    private val _liveDataRepoIssuesPagedList: LiveData<PagedList<GitHubRepoIssueItem>> = Transformations.switchMap(_selectedRepoLiveData) {
        logger.v(TAG, "_liveDataRepoIssuesPagedList: Building livePagedList")
        LivePagedListBuilder(it, pagedListConfig)
            .setBoundaryCallback(GitHubRepoIssuesBoundaryCallback())
            .build()
    }
    val liveDataRepoIssuesPagedList: LiveData<PagedList<GitHubRepoIssueItem>>
        get() = _liveDataRepoIssuesPagedList

    private val pagedListConfig: PagedList.Config

    val networkFetchStatusLiveData = appRepository.isFetchInProgress
    val networkErrorStatusLiveData = appRepository.networkError

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

    fun selectRepo(repoName: String) {
        logger.i(TAG, "selectRepo(): repoName: $repoName")
        _selectedRepoNameLiveData.postValue(repoName)
    }

    fun loadData() {
        scope.launch(dispatcherProvider.ioDispatcher) {

            val ownerName = appSharedPrefs.getGitHubRepoOwner()
            val repoName = appSharedPrefs.getGitHubRepoName()
            logger.i(TAG, "loadData(): ownerName: $ownerName, repoName: $repoName")

            appRepository.loadGitHubRepoIssuesList(ownerName, repoName)

        }
    }

    fun onRefresh() = appRepository.refreshRepoIssuesList().also { selectRepo("") }

    fun onClearCache() = scope.launch(dispatcherProvider.ioDispatcher) {
        logger.d(TAG, "onClearCache(): ")
        async { appRepository.clearRepoIssuesCache() }.await()
        async { appRepository.clearReposCache() }.await()
    }

    override fun onCleared() {

        logger.d(TAG, "onCleared(): ")
        super.onCleared()

    }

    inner class GitHubRepoIssuesBoundaryCallback: BaseBoundaryCallback<GitHubRepoIssueItem>(logger, appRepository) {

        override fun requestData(isLastItem: Boolean) = when (appRepository.isFetchInProgress.value!!) {

            true -> logger.v(TAG_REPO_BOUNDARY_CALLBACK, "requestData(): " +
                    "isLastItem = $isLastItem, NETWORK FETCH is already in progress")
            false -> {

                val ownerName = appSharedPrefs.getGitHubRepoOwner()
                val repoName = appSharedPrefs.getGitHubRepoName()

                logger.i(TAG_REPO_BOUNDARY_CALLBACK, "requestData(): " +
                        "isLastItem = $isLastItem, ownerName: $ownerName, repoName: $repoName")

                loadData()

            }
        }

    }

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
        private val TAG_REPO_BOUNDARY_CALLBACK: String = GitHubRepoIssuesBoundaryCallback::class.java.simpleName

        const val DATABASE_PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 5
    }

}