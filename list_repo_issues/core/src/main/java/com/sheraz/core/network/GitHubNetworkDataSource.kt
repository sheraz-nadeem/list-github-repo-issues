package com.sheraz.core.network

import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity

/**
 * An interface that is responsible to provide methods for
 * initiating network requests.
 *
 * Following the principle of `Programming to an interface`,
 * this interface is used to provide methods that any
 * implementation class must provide in order for other
 * application components to work seamlessly.
 */

interface GitHubNetworkDataSource {

    suspend fun loadRepoIssuesFromNetwork(ownerName: String, repoName: String, pageSize: Int, page: Int): List<GitHubRepoIssueEntity>

    companion object {
        const val ERROR_MESSAGE = "Error loading github repos data"
    }
}