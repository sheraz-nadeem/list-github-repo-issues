package com.sheraz.core.network

import com.sheraz.core.network.response.GetGitHubRepoIssuesResponse

interface GitHubNetworkDataSource {

    suspend fun fetchRepoIssuesFromNetwork(repoName: String, pageSize: Int, page: Int): GetGitHubRepoIssuesResponse

    companion object {
        const val ERROR_MESSAGE = "Error loading github repos data"
    }
}