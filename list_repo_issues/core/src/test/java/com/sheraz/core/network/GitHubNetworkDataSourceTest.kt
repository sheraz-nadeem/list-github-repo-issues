package com.sheraz.core.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sheraz.core.data.db.entity.GitHubRepoEntity
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity
import com.sheraz.core.shared.awesomeTensorflowRepo
import com.sheraz.core.shared.tensorflowModelsRepo
import com.sheraz.core.shared.tensorflowModelsRepoIssue1
import com.sheraz.core.shared.tensorflowModelsRepoIssue2
import com.sheraz.core.utils.Logger
import io.mockk.mockk
import org.junit.Rule

/**
 * Class that contains unit tests for [GitHubNetworkDataSource] by mocking
 * [GitHubApiService] instance
 */
class GitHubNetworkDataSourceTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repoList: List<GitHubRepoEntity> = listOf(tensorflowModelsRepo, awesomeTensorflowRepo)
    private val repoIssuesList: List<GitHubRepoIssueEntity> = listOf(tensorflowModelsRepoIssue1, tensorflowModelsRepoIssue2)

    private val logger = mockk<Logger>()
    private val gitHubApiService = mockk<GitHubApiService>()

}