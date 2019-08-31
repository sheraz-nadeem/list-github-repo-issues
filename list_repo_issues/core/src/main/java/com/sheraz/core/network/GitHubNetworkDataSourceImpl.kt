package com.sheraz.core.network

import android.content.Context
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity
import com.sheraz.core.network.response.GetGitHubRepoIssuesResponse
import com.sheraz.core.utils.Logger


class GitHubNetworkDataSourceImpl(
    private val logger: Logger,
    private val gitHubApiService: GitHubApiService
) : GitHubNetworkDataSource {

    /**
     * Public method to initiate network request. Since, we are
     * doing IO operations with API using Retrofit, this method should
     * be a suspend function and called from a coroutine or another suspend function.
     *
     * @param repoFullName String Full Name of the GitHub repository
     * @param pageSize Int count for number of items to include in response
     * @param page Int used for page number
     *
     * @return [GetGitHubRepoIssuesResponse] response object
     */
    override suspend fun loadRepoIssuesFromNetwork(ownerName: String, repoName: String, pageSize: Int, page: Int): List<GitHubRepoIssueEntity> {

        logger.d(TAG, "loadRepoIssuesFromNetwork(): ownerName: $ownerName, repoName: $repoName, pageSize: $pageSize, page: $page")
        return fetchRepoIssuesFromNetwork(ownerName, repoName, pageSize, page)

    }

    /**
     * Private method to get Repos using
     * {@see com.sheraz.core.network.GitHubApiService#getRepoIssues} and return
     * the received list of GitHub Repository issues
     *
     * @param repoFullName String Full Name of the GitHub repository
     * @param pageSize Int count for number of items to include in response
     * @param page Int used for page number
     *
     * @return [GetGitHubRepoIssuesResponse] response object
     */
    private suspend fun fetchRepoIssuesFromNetwork(ownerName: String, repoName: String, pageSize: Int, page: Int): List<GitHubRepoIssueEntity> {

        logger.d(TAG, "fetchRepoIssuesFromNetwork(): ownerName: $ownerName, repoName: $repoName, pageSize: $pageSize, page: $page")

        val response = gitHubApiService.getRepoIssues(ownerName, repoName, pageSize, page).await()

        if (response.isSuccessful) {

            val responseBody = response.body()
            if (responseBody != null) {
                return responseBody
            }

        }

        return emptyList()
    }

    companion object {

        private val TAG: String = GitHubNetworkDataSourceImpl::class.java.simpleName

        @Volatile
        private var instance: GitHubNetworkDataSource? = null

        operator fun invoke(context: Context): GitHubNetworkDataSource = instance ?: synchronized(this) {
            return@synchronized instance ?: buildNetworkDataSource(context).also { instance = it }
        }

        private fun buildNetworkDataSource(context: Context) =
            GitHubNetworkDataSourceImpl(Logger(), GitHubApiService(context))

    }
}