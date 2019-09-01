package com.sheraz.core.network

import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity
import com.sheraz.core.network.response.Result
import com.sheraz.core.utils.Logger
import com.sheraz.core.utils.safeApiCall
import java.io.IOException


/**
 * This class is responsible for providing interfacing
 * with [GitHubApiService], to fetch & return network
 * responses.
 */

class GitHubNetworkDataSourceImpl(
    private val logger: Logger,
    private val gitHubApiService: GitHubApiService
) : GitHubNetworkDataSource {

    /**
     * Public method to initiate network request. Since, we are
     * doing IO operations with API using Retrofit, this method should
     * be a suspend function and called from a coroutine or another suspend function.
     *
     * @param ownerName String Owner Name of the GitHub repository
     * @param repoName String Repository Name of the GitHub repository
     * @param pageSize Int count for number of items to include in response
     * @param page Int used for page number
     *
     * @return List<GitHubRepoIssueEntity> list of entity objects
     */
    override suspend fun loadRepoIssuesFromNetwork(ownerName: String,
                                                   repoName: String,
                                                   pageSize: Int,
                                                   page: Int) = safeApiCall(
        networkBlock = { fetchRepoIssuesFromNetwork(ownerName, repoName, pageSize, page) },
        errorMessage = "Unable to load repository issues"
    )

    /**
     * Private method to get Repos using
     * {@see com.sheraz.core.network.GitHubApiService#getRepoIssuesAsync} and return
     * the received list of GitHub Repository issues
     *
     * @param ownerName String Owner Name of the GitHub repository
     * @param repoName String Repository Name of the GitHub repository
     * @param pageSize Int count for number of items to include in response
     * @param page Int used for page number
     *
     * @return List<GitHubRepoIssueEntity> list of entity objects
     */
    private suspend fun fetchRepoIssuesFromNetwork(ownerName: String,
                                                   repoName: String,
                                                   pageSize: Int,
                                                   page: Int): Result<List<GitHubRepoIssueEntity>> {

        logger.d(TAG, "fetchRepoIssuesFromNetwork(): ownerName: $ownerName, repoName: $repoName, pageSize: $pageSize, page: $page")

        val response = gitHubApiService.getRepoIssuesAsync(ownerName, repoName, pageSize, page).await()

        if (response?.isSuccessful) {
            logger.i(TAG, "fetchRepoIssuesFromNetwork(): response is successful")
            val responseBody = response.body()
            if (responseBody != null) {
                return Result.Success(responseBody)
            }

        }

        return Result.Error(IOException(" Throwing exception ${response.code()} ${response.message()}"))
    }

    companion object {

        private val TAG: String = GitHubNetworkDataSourceImpl::class.java.simpleName

        @Volatile
        private var instance: GitHubNetworkDataSource? = null

        operator fun invoke(logger: Logger, apiService: GitHubApiService): GitHubNetworkDataSource = instance ?: synchronized(this) {
            return@synchronized instance ?: buildNetworkDataSource(logger, apiService).also { instance = it }
        }

        private fun buildNetworkDataSource(logger: Logger, apiService: GitHubApiService) =
            GitHubNetworkDataSourceImpl(logger, apiService)

    }
}