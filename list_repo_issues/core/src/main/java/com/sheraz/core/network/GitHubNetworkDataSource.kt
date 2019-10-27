package com.sheraz.core.network

import com.sheraz.core.data.db.entity.GitHubRepoEntity
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity
import com.sheraz.core.network.response.Result

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

    suspend fun loadRepoIssuesFromNetwork(ownerName: String, repoName: String, pageSize: Int, page: Int): Result<List<GitHubRepoIssueEntity>>
    suspend fun loadReposFromNetwork(query: String, pageSize: Int, page: Int): Result<List<GitHubRepoEntity>>

    companion object {
        const val ERROR_MESSAGE_REPO_ISSUES = "Unable to load github repository issues"
        const val ERROR_MESSAGE_REPOS = "Unable to load github repositories"
    }
}