package com.sheraz.core.network

import com.sheraz.core.data.db.entity.GitHubRepoEntity
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity
import com.sheraz.core.network.GitHubNetworkDataSource.Companion.ERROR_MESSAGE_REPO_ISSUES
import com.sheraz.core.network.response.Result
import com.sheraz.core.utils.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


/**
 * An interface as an example for providing [GitHubNetworkDataSourceWithConfig]
 * instance with [NetworkConfig] using @Assisted. Its implementation will be generated
 * by Dagger.
 */
@AssistedFactory
interface GitHubNetworkDataSourceWithConfigFactory {
    fun create(config: NetworkConfig): GitHubNetworkDataSourceWithConfigImpl
}

/**
 * This class is just a dummy implementation to show an example for providing
 * instances with @AssistedInject.
 * Note: Instances using @AssistedInject cannot be scoped
 */
class GitHubNetworkDataSourceWithConfigImpl @AssistedInject constructor(
    private val logger: Logger,
    private val gitHubApiService: GitHubApiService,
    @Assisted private val config: NetworkConfig,
) : GitHubNetworkDataSourceWithConfig {

    override suspend fun loadRepoIssuesFromNetwork(
        ownerName: String,
        repoName: String,
        pageSize: Int,
        page: Int
    ): Result<List<GitHubRepoIssueEntity>> {
        // TODO: Just return error as this is just a dummy class
        val result = Result.Error(Exception(ERROR_MESSAGE_REPO_ISSUES))
        logger.d(TAG, "loadRepoIssuesFromNetwork: result = $result")
        logger.d(TAG, "loadRepoIssuesFromNetwork: gitHubApiService = $gitHubApiService")
        logger.d(TAG, "loadRepoIssuesFromNetwork: config = $config")
        return result
    }

    override suspend fun loadReposFromNetwork(
        query: String,
        pageSize: Int,
        page: Int
    ): Result<List<GitHubRepoEntity>> {
        val result = Result.Error(Exception(ERROR_MESSAGE_REPO_ISSUES))
        logger.d(TAG, "loadReposFromNetwork: result = $result")
        logger.d(TAG, "loadReposFromNetwork: gitHubApiService = $gitHubApiService")
        logger.d(TAG, "loadReposFromNetwork: config = $config")
        return result
    }
}
private const val TAG = "GitHubNetworkDataSourceWithConfigImpl"