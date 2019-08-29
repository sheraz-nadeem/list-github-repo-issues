package com.sheraz.core.network

import com.sheraz.core.network.response.GetGitHubRepoIssuesResponse


class GitHubNetworkDataSourceImpl: GitHubNetworkDataSource {

    /**
     * This method uses [GitHubApiService] to initiate network fetch
     * for the provided GitHub repository name
     */
    override suspend fun fetchRepoIssuesFromNetwork(repoName: String, pageSize: Int, page: Int): GetGitHubRepoIssuesResponse {
        // TODO Implementation
        return GetGitHubRepoIssuesResponse(emptyList())
    }

    companion object {
        private val TAG: String = GitHubNetworkDataSourceImpl::class.java.simpleName
    }
}