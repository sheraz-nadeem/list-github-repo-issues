package com.sheraz.core.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sheraz.core.data.db.datasourcefactory.GitHubRepoEntityDataSourceFactory
import com.sheraz.core.data.db.dao.GitHubRepoEntityDao
import com.sheraz.core.data.db.dao.GitHubRepoIssueEntityDao
import com.sheraz.core.data.db.datasourcefactory.GitHubRepoIssueDataSourceFactory
import com.sheraz.core.data.db.entity.GitHubRepoEntity
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity
import com.sheraz.core.network.GitHubNetworkDataSource
import com.sheraz.core.network.response.Result
import com.sheraz.core.utils.Logger
import kotlin.Exception

/**
 * This class is responsible for interacting with data sources
 * [GitHubRepoIssueEntityDao] & [GitHubNetworkDataSource]
 * This class is also responsible for generating events so that
 * a UI component/subscriber can update its state.
 */

class AppRepositoryImpl(
    private val logger: Logger,
    private val gitHubRepoIssueEntityDao: GitHubRepoIssueEntityDao,
    private val gitHubRepoEntityDao: GitHubRepoEntityDao,
    private val gitHubRepoEntityDataSourceFactory: GitHubRepoEntityDataSourceFactory,
    private val gitHubRepoIssueDataSourceFactory: GitHubRepoIssueDataSourceFactory,
    private val gitHubNetworkDataSource: GitHubNetworkDataSource
): AppRepository {

    private val _isFetchInProgress = MutableLiveData<Boolean>().apply { postValue(false) }
    override val isFetchInProgress: LiveData<Boolean>
        get() = _isFetchInProgress

    private val _networkError = MutableLiveData<Exception>()
    override val networkError: LiveData<Exception>
        get() = _networkError

    private val _noMoreItemsAvailable = MutableLiveData<Boolean>().apply { postValue(false) }
    override val noMoreItemsAvailable: LiveData<Boolean>
        get() = _noMoreItemsAvailable


    init {
        logger.d(TAG, "init(): ")
    }

    override fun resetNoMoreItemsAvailable() {
        logger.d(TAG, "resetNoMoreItemsAvailable(): ")
        _noMoreItemsAvailable.postValue(false)
    }

    override fun getAllRepoIssuesPagedFactory(ownerName: String, repoName: String) =
        gitHubRepoIssueDataSourceFactory.setRepoAndQuery(ownerName, repoName).getDataSourceFactoryForEntity()
    override fun getAllReposPagedFactory(repoName: String) =
        gitHubRepoEntityDataSourceFactory.setRepoAndQuery(repoName).getDataSourceFactoryForEntity()

    /**
     * Method to return all issues live data
     */
    override fun getAllReposLiveData(): LiveData<List<GitHubRepoEntity>> {
        logger.d(TAG, "getAllReposLiveData(): ")
        return gitHubRepoEntityDao.getAllRepos()
    }

    /**
     * Method to return all issues live data
     */
    override fun getAllRepoIssuesLiveData(): LiveData<List<GitHubRepoIssueEntity>> {
        logger.d(TAG, "getAllRepoIssuesLiveData(): ")
        return gitHubRepoIssueEntityDao.getAllRepoIssues()
    }

    /**
     * Method to return only open issues live data
     */
    override fun getOpenIssuesLiveData(): LiveData<List<GitHubRepoIssueEntity>> {
        logger.d(TAG, "getOpenIssuesLiveData(): ")
        return gitHubRepoIssueEntityDao.getOpenIssuesInRepo()
    }

    /**
     * Method to return only closed issues live data
     */
    override fun getClosedIssuesLiveData(): LiveData<List<GitHubRepoIssueEntity>> {
        logger.d(TAG, "getClosedIssuesLiveData(): ")
        return gitHubRepoIssueEntityDao.getClosedIssuesInRepo()
    }

    /**
     * Method to refresh repository issues list
     */
    override fun refreshRepoIssuesList() {
        logger.d(TAG, "refreshRepoIssuesList(): ")
        gitHubRepoIssueDataSourceFactory.refresh()
    }

    /**
     * Method to clear local [GitHubRepoIssueEntityDao] database table
     */
    override suspend fun clearRepoIssuesCache() {

        logger.d(TAG, "clearRepoIssuesCache(): ")
        try {

            val numOfRowsDeleted = gitHubRepoIssueEntityDao.deleteAll()
            logger.i(TAG, "clearRepoIssuesCache(): numOfRowsDeleted: $numOfRowsDeleted")

        } catch (e: Exception) {

            logger.e(TAG, "clearRepoIssuesCache(): Exception occurred, Error => ${e.message}")

        }

    }

    /**
     * Method to clear local [GitHubRepoEntityDao] database table
     */
    override suspend fun clearReposCache() {

        logger.d(TAG, "clearReposCache(): ")
        try {

            val numOfRowsDeleted = gitHubRepoEntityDao.deleteAll()
            logger.i(TAG, "clearReposCache(): numOfRowsDeleted: $numOfRowsDeleted")

        } catch (e: Exception) {

            logger.e(TAG, "clearReposCache(): Exception occurred, Error => ${e.message}")

        }

    }

    /**
     * Method that sends request using [GitHubNetworkDataSource] and also persist data in local cache
     */
    override suspend fun loadGitHubRepoIssuesList(ownerName: String,
                                                  repoName: String,
                                                  pageSize: Int,
                                                  page: Int) {
        logger.d(TAG, "loadGitHubRepoIssuesList(): ")
        fetchGitHubRepoIssuesAndPersist(ownerName, repoName, pageSize, page)
    }

    /**
     * Method that initiates network request and also persists data in local cache
     */
    private suspend fun fetchGitHubRepoIssuesAndPersist(ownerName: String = "",
                                                        repoName: String = "",
                                                        pageSize: Int = AppRepository.NETWORK_PAGE_SIZE,
                                                        page: Int = 1) {

        logger.d(TAG,"fetchGitHubRepoIssuesAndPersist(): ")

        val response = fetchRepoIssuesFromNetworkAndPersist(ownerName, repoName, pageSize, page)
        _isFetchInProgress.postValue(false)

        when (response) {
            is Result.Success -> persistDownloadedGitHubRepoIssuesList(response.data)
            is Result.Error -> _networkError.postValue(response.exception)
        }

    }

    /**
     * Method that sends request using [GitHubNetworkDataSource] and returns
     * [List<GitHubRepoIssueEntity>] response wrapped inside [Result]
     */
    private suspend fun fetchRepoIssuesFromNetworkAndPersist(ownerName: String = "",
                                                             repoName: String = "",
                                                             pageSize: Int = AppRepository.NETWORK_PAGE_SIZE,
                                                             page: Int = 1): Result<List<GitHubRepoIssueEntity>> {

        logger.d(TAG, "fetchRepoIssuesFromNetworkAndPersist(): ownerName: $ownerName, repoName: $repoName, pageSize: $pageSize, page: $page")

        _isFetchInProgress.postValue(true)
        val numOfRows = getNumOfRowsIssueEntity(gitHubRepoIssueDataSourceFactory.getLikeQueryForEntity())
        val actualPage = getActualPage(page, numOfRows)
        logger.i(TAG,"fetchRepoIssuesFromNetworkAndPersist(): numOfRows: $numOfRows, actualPage: $actualPage")

        return gitHubNetworkDataSource.loadRepoIssuesFromNetwork(ownerName, repoName, pageSize, actualPage)

    }

    /**
     * Method that persists data in [GitHubRepoIssueEntityDao] database table
     */
    private fun persistDownloadedGitHubRepoIssuesList(gitHubRepoIssueEntityList: List<GitHubRepoIssueEntity>) {

        try {

            if (gitHubRepoIssueEntityList.size < AppRepository.NETWORK_PAGE_SIZE) {
                _noMoreItemsAvailable.postValue(true)
            } else {
                _noMoreItemsAvailable.postValue(false)
            }

            logger.d(TAG,"persistDownloadedGitHubRepoIssuesList(): gitHubRepoIssueEntityList.size: ${gitHubRepoIssueEntityList.size}")

            if (gitHubRepoIssueEntityList.isNotEmpty()) {
                gitHubRepoIssueEntityDao.insertList(gitHubRepoIssueEntityList)
            }

        } catch (e: Exception) {

            logger.e(TAG,"persistDownloadedGitHubRepoIssuesList(): Exception occurred, Error => ${e.localizedMessage}")

        }

    }

    /**
     * Method that sends request using [GitHubNetworkDataSource] and also persist data in local cache
     */
    override suspend fun loadGitHubReposList(query: String,
                                             pageSize: Int,
                                             page: Int) {
        logger.d(TAG, "loadGitHubReposList(): ")
        fetchGitHubReposAndPersist(query, pageSize, page)
    }

    /**
     * Method that initiates network request and also persists data in local cache
     */
    private suspend fun fetchGitHubReposAndPersist(query: String = "",
                                                   pageSize: Int = AppRepository.NETWORK_PAGE_SIZE,
                                                   page: Int = 1) {

        logger.d(TAG,"fetchGitHubReposAndPersist(): ")

        val response = fetchReposFromNetworkAndPersist(query, pageSize, page)
        _isFetchInProgress.postValue(false)

        when (response) {
            is Result.Success -> persistDownloadedGitHubReposList(response.data)
            is Result.Error -> _networkError.postValue(response.exception)
        }

    }

    /**
     * Method that sends request using [GitHubNetworkDataSource] and returns
     * [List<GitHubRepoIssueEntity>] response wrapped inside [Result]
     */
    private suspend fun fetchReposFromNetworkAndPersist(query: String = "",
                                                             pageSize: Int = AppRepository.NETWORK_PAGE_SIZE,
                                                             page: Int = 1): Result<List<GitHubRepoEntity>> {

        logger.d(TAG, "fetchReposFromNetworkAndPersist(): query: $query, pageSize: $pageSize, page: $page")

        _isFetchInProgress.postValue(true)
        val numOfRows = getNumOfRowsRepoEntity(gitHubRepoEntityDataSourceFactory.getLikeQueryForEntity())
        val actualPage = getActualPage(page, numOfRows)
        logger.i(TAG,"fetchReposFromNetworkAndPersist(): numOfRows: $numOfRows, actualPage: $actualPage")

        return gitHubNetworkDataSource.loadReposFromNetwork(query.plus("+in:name,description"), pageSize, actualPage)

    }

    /**
     * Method that persists data in [GitHubRepoIssueEntityDao] database table
     */
    private fun persistDownloadedGitHubReposList(gitHubRepoEntityList: List<GitHubRepoEntity>) {

        try {

            if (gitHubRepoEntityList.size < AppRepository.NETWORK_PAGE_SIZE) {
                _noMoreItemsAvailable.postValue(true)
            } else {
                _noMoreItemsAvailable.postValue(false)
            }

            logger.d(TAG,"persistDownloadedGitHubReposList(): gitHubRepoEntityList.size: ${gitHubRepoEntityList.size}")

            if (gitHubRepoEntityList.isNotEmpty()) {
                gitHubRepoEntityDao.insertList(gitHubRepoEntityList)
            }

        } catch (e: Exception) {

            logger.e(TAG,"persistDownloadedGitHubReposList(): Exception occurred, Error => ${e.localizedMessage}")

        }

    }

    private fun getNumOfRowsIssueEntity(query: String) = gitHubRepoIssueEntityDao.getNumOfRowsForQuery(query)
    private fun getNumOfRowsRepoEntity(query: String) = gitHubRepoEntityDao.getNumOfRowsForQuery(query)

    private fun getActualPage(page: Int, numOfRows: Int): Int {
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
                            gitHubRepoIssueEntityDao: GitHubRepoIssueEntityDao,
                            gitHubRepoEntityDao: GitHubRepoEntityDao,
                            gitHubRepoEntityDataSourceFactory: GitHubRepoEntityDataSourceFactory,
                            gitHubRepoIssueDataSourceFactory: GitHubRepoIssueDataSourceFactory,
                            networkDataSource: GitHubNetworkDataSource): AppRepository = instance ?: synchronized(this) {
            return@synchronized instance ?: buildAppRepository(
                logger,
                gitHubRepoIssueEntityDao,
                gitHubRepoEntityDao,
                gitHubRepoEntityDataSourceFactory,
                gitHubRepoIssueDataSourceFactory,
                networkDataSource
            ).also { instance = it }
        }

        private fun buildAppRepository(logger: Logger,
                                       gitHubRepoIssueEntityDao: GitHubRepoIssueEntityDao,
                                       gitHubRepoEntityDao: GitHubRepoEntityDao,
                                       gitHubRepoEntityDataSourceFactory: GitHubRepoEntityDataSourceFactory,
                                       gitHubRepoIssueDataSourceFactory: GitHubRepoIssueDataSourceFactory,
                                       networkDataSource: GitHubNetworkDataSource) =
            AppRepositoryImpl(
                logger,
                gitHubRepoIssueEntityDao,
                gitHubRepoEntityDao,
                gitHubRepoEntityDataSourceFactory,
                gitHubRepoIssueDataSourceFactory,
                networkDataSource
            )

    }
}