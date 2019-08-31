package com.sheraz.core.network

import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity

interface GitHubNetworkDataSource {

    suspend fun loadRepoIssuesFromNetwork(ownerName: String, repoName: String, pageSize: Int, page: Int): List<GitHubRepoIssueEntity>

    companion object {
        const val ERROR_MESSAGE = "Error loading github repos data"
    }
}