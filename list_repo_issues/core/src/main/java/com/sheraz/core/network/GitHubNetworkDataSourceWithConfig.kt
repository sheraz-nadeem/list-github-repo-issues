package com.sheraz.core.network

import com.sheraz.core.data.db.entity.GitHubRepoEntity
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.core.network.response.Result
import org.json.JSONObject

/**
 * An interface that is responsible to provide methods for
 * initiating network requests with config.
 *
 * Following the principle of `Programming to an interface`,
 * this interface is used to provide methods that any
 * implementation class must provide in order for other
 * application components to work seamlessly.
 */

interface GitHubNetworkDataSourceWithConfig {

    suspend fun loadRepoIssuesFromNetwork(
        ownerName: String,
        repoName: String,
        pageSize: Int = AppRepository.NETWORK_PAGE_SIZE,
        page: Int = 1
    ): Result<List<GitHubRepoIssueEntity>>

    suspend fun loadReposFromNetwork(query: String, pageSize: Int, page: Int): Result<List<GitHubRepoEntity>>
}

data class NetworkConfig(
    val config: JSONObject
)