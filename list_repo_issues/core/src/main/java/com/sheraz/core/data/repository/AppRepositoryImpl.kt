package com.sheraz.core.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sheraz.core.data.db.dao.GitHubRepoIssueEntityDao
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity
import com.sheraz.core.network.GitHubNetworkDataSource
import com.sheraz.core.network.response.GetGitHubRepoIssuesResponse
import com.sheraz.core.utils.Logger
import kotlin.Exception

class AppRepositoryImpl(
    private val logger: Logger,
    private val gitHubRepoIssueEntityDao: GitHubRepoIssueEntityDao,
    private val gitHubNetworkDataSource: GitHubNetworkDataSource
): AppRepository {

    private val _isFetchInProgress = MutableLiveData<Boolean>().apply { postValue(false) }
    override val isFetchInProgress: LiveData<Boolean>
        get() = _isFetchInProgress

    private val _networkError = MutableLiveData<Exception>()
    override val networkError: LiveData<Exception>
        get() = _networkError


    init {
        logger.d(TAG, "init(): ")
    }

    /**
     * Method to return incoming & outgoing call recordings live data
     */
    override fun getAllRepoIssuesLiveData(): LiveData<List<GitHubRepoIssueEntity>> {
        logger.d(TAG, "getAllRepoIssuesLiveData(): ")
        return gitHubRepoIssueEntityDao.getAllRepoIssues()
    }

    /**
     * Method to return only incoming call recordings live data
     */
    override fun getOpenIssuesLiveData(): LiveData<List<GitHubRepoIssueEntity>> {
        logger.d(TAG, "getOpenIssuesLiveData(): ")
        return gitHubRepoIssueEntityDao.getOpenIssuesInRepo()
    }

    /**
     * Method to return only outgoing call recordings live data
     */
    override fun getClosedIssuesLiveData(): LiveData<List<GitHubRepoIssueEntity>> {
        logger.d(TAG, "getClosedIssuesLiveData(): ")
        return gitHubRepoIssueEntityDao.getClosedIssuesInRepo()
    }

    /**
     * Method to clear local [GitHubRepoIssueEntityDao] database table
     */
    override suspend fun clearCache() {

        logger.d(TAG, "clearCache(): ")
        try {

            val numOfRowsDeleted = gitHubRepoIssueEntityDao.deleteAll()
            logger.i(TAG, "clearCache(): numOfRowsDeleted: $numOfRowsDeleted")

        } catch (e: Exception) {

            logger.e(TAG, "clearCache(): Exception occurred, Error => ${e.message}")

        }

    }

    /**
     * Method that sends request using [GitHubNetworkDataSource] and also persist data in local cache
     */
    override suspend fun loadGitHubRepoIssuesList(ownerName: String, repoName: String, pageSize: Int, page: Int) {
        logger.d(TAG, "loadGitHubRepoIssuesList(): ")
        fetchGitHubRepoIssuesAndPersist(ownerName, repoName, pageSize, page)
    }

    /**
     * Method that refreshes list of call recordings
     */
    override suspend fun refreshGitHubRepoIssuesList() {
        logger.d(TAG, "refreshCallRecordingsList(): ")
        fetchGitHubRepoIssuesAndPersist("", "", AppRepository.NETWORK_PAGE_SIZE, -1)
    }

    /**
     * Method that initiates network request and also persists data in local cache
     */
    private suspend fun fetchGitHubRepoIssuesAndPersist(ownerName: String = "", repoName: String = "", pageSize: Int = AppRepository.NETWORK_PAGE_SIZE, page: Int = 1) {

        logger.d(TAG,"fetchGitHubRepoIssuesAndPersist(): ")

        val response = fetchRepoIssuesFromNetworkAndPersist(ownerName, repoName, pageSize, page)
        if (response != null) {
            logger.d(TAG,"fetchGitHubRepoIssuesAndPersist(): response is success")
            _isFetchInProgress.postValue(false)
            persistDownloadedGitHubRepoIssuesList(response)
        }

    }

    /**
     * Method that sends request using [GitHubNetworkDataSource] and returns
     * [GetGitHubRepoIssuesResponse] response wrapped inside [Result]
     */
    private suspend fun fetchRepoIssuesFromNetworkAndPersist(ownerName: String = "", repoName: String = "", pageSize: Int = AppRepository.NETWORK_PAGE_SIZE, page: Int = 1): List<GitHubRepoIssueEntity> {

        logger.d(TAG, "fetchRepoIssuesFromNetworkAndPersist(): ownerName: $ownerName, repoName: $repoName, pageSize: $pageSize, page: $page")

        _isFetchInProgress.postValue(true)
        val numOfRows = getNumOfRows()
        val actualPageSize = getActualPageSize(page, numOfRows)
        logger.i(TAG,"fetchRepoIssuesFromNetworkAndPersist(): numOfRows: $numOfRows, actualPageSize: $actualPageSize")

        return gitHubNetworkDataSource.loadRepoIssuesFromNetwork(ownerName, repoName, pageSize, actualPageSize)

    }

    /**
     * Method that persists data in [GitHubRepoIssueEntityDao] database table
     */
    private fun persistDownloadedGitHubRepoIssuesList(gitHubRepoIssueEntityList: List<GitHubRepoIssueEntity>) {

        logger.d(TAG,"persistDownloadedGitHubRepoIssuesList(): gitHubRepoIssueEntityList.size: ${gitHubRepoIssueEntityList.size}")

        try {

            if (gitHubRepoIssueEntityList.isNotEmpty()) {
                gitHubRepoIssueEntityDao.insertList(gitHubRepoIssueEntityList)
            }

        } catch (e: Exception) {

            logger.e(TAG,"persistDownloadedGitHubRepoIssuesList(): Exception occurred, Error => ${e.localizedMessage}")

        }

    }

    private fun getNumOfRows(): Int {
        return gitHubRepoIssueEntityDao.getNumOfRows()
    }

    private fun getActualPageSize(page: Int, numOfRows: Int): Int {
        return when (page > 0) {
            true -> (numOfRows / AppRepository.NETWORK_PAGE_SIZE) + 1
            false -> 1 // We need to refresh data
        }
    }


    companion object {

        private val TAG: String = AppRepositoryImpl::class.java.simpleName

        @Volatile
        private var instance: AppRepository? = null

        operator fun invoke(logger: Logger,
                            dao: GitHubRepoIssueEntityDao,
                            networkDataSource: GitHubNetworkDataSource): AppRepository = instance ?: synchronized(this) {
            return@synchronized instance ?: buildAppRepository(logger, dao, networkDataSource).also { instance = it }
        }

        private fun buildAppRepository(logger: Logger,
                                       dao: GitHubRepoIssueEntityDao,
                                       networkDataSource: GitHubNetworkDataSource) =
            AppRepositoryImpl(logger, dao, networkDataSource)

    }
}