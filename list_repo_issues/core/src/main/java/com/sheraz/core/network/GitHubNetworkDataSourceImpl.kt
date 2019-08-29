package com.sheraz.core.network

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
    override suspend fun loadRepoIssuesFromNetwork(repoFullName: String, pageSize: Int, page: Int): GetGitHubRepoIssuesResponse {

        logger.d(TAG, "loadRepoIssuesFromNetwork(): repoFullName: $repoFullName, pageSize: $pageSize, page: $page")
        return fetchRepoIssuesFromNetwork(repoFullName, pageSize, page)

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
    private suspend fun fetchRepoIssuesFromNetwork(repoFullName: String, pageSize: Int, page: Int): GetGitHubRepoIssuesResponse {

        logger.d(TAG, "fetchRepoIssuesFromNetwork(): repoFullName: $repoFullName, pageSize: $pageSize, page: $page")

        val response = gitHubApiService.getRepoIssues(repoFullName, pageSize, page).await()

        if (response.isSuccessful) {

            val responseBody = response.body()
            if (responseBody != null) {
                logger.i(TAG, "fetchRepoIssuesFromNetwork(): response: $responseBody")
                return responseBody
            }

        }

        return GetGitHubRepoIssuesResponse(emptyList())
    }

    companion object {
        private val TAG: String = GitHubNetworkDataSourceImpl::class.java.simpleName
    }
}