package com.sheraz.core.network

import com.sheraz.core.base.BaseTest
import com.sheraz.core.data.db.entity.GitHubRepoEntity
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity
import com.sheraz.core.network.GitHubNetworkDataSource.Companion.ERROR_MESSAGE_REPO_ISSUES
import com.sheraz.core.network.response.Result
import com.sheraz.core.shared.awesomeTensorflowRepo
import com.sheraz.core.shared.tensorflowModelsRepo
import com.sheraz.core.shared.tensorflowModelsRepoIssue1
import com.sheraz.core.shared.tensorflowModelsRepoIssue2
import io.mockk.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException


/**
 * Class that contains unit tests for [GitHubNetworkDataSource] by mocking
 * [GitHubApiService] instance
 */
class GitHubNetworkDataSourceTest: BaseTest() {

    private val repoList: List<GitHubRepoEntity> = listOf(tensorflowModelsRepo, awesomeTensorflowRepo)

    private val repoIssuesList: List<GitHubRepoIssueEntity> = listOf(tensorflowModelsRepoIssue1, tensorflowModelsRepoIssue2)
    private val repoIssuesSuccessResponse = Response.success(repoIssuesList)
    private val repoIssuesSuccessResult = Result.Success(repoIssuesList)

    private val repoIssuesFailureResponse = Response.error<List<GitHubRepoIssueEntity>>(400, ResponseBody.create(null, ERROR_MESSAGE_REPO_ISSUES))
    private val repoIssuesFailureResult = Result.Error(IOException(ERROR_MESSAGE_REPO_ISSUES))

    private val gitHubApiService = mockk<GitHubApiService>()

    private lateinit var networkDataSource: GitHubNetworkDataSourceImpl


    @Before
    fun buildNetworkDataSource() {
        networkDataSource = GitHubNetworkDataSourceImpl(logger, gitHubApiService)
    }

    @Test
    fun `test when load happens then fetch happens only once and response is success`() = runBlocking {

        val ownerName = "tensorflow"
        val repoName = "model"
        val page = 1
        val pageSize = 50

        // Given that api service responds with success
        val successResponse = CompletableDeferred(repoIssuesSuccessResponse)
        coEvery { gitHubApiService.getRepoIssuesAsync(ownerName, repoName, pageSize, page) } returns successResponse

        // When load repo issues happens
        val actualResult = networkDataSource.loadRepoIssuesFromNetwork(ownerName, repoName, pageSize, page)

        // Then verify that api service invokes correct method
        verify (exactly = 1) { gitHubApiService.getRepoIssuesAsync(ownerName, repoName, pageSize, page) }
        assertEquals(repoIssuesSuccessResult, actualResult)


        /** Same Unit test as above but using Mockito-Kotlin
        // Given that api service responds with success
        val expectedResult = CompletableDeferred(repoIssuesSuccessResponse)
        whenever(gitHubApiService.getRepoIssuesAsync(ownerName, repoName, pageSize, page)).thenReturn(expectedResult)

        // When load repo issues happens
        val actualResult = networkDataSource.loadRepoIssuesFromNetwork(ownerName, repoName, pageSize, page)

        // Then verify that api service invokes correct method
        verify(gitHubApiService, times(1)).getRepoIssuesAsync(ownerName, repoName, pageSize, page)
        assertEquals(Result.Success(repoIssuesList), actualResult)
        **/
    }

    @Test
    fun `test when load happens then fetch happens only once and response is failure`() = runBlocking {

        val ownerName = "tensorflow"
        val repoName = "model"
        val page = 1
        val pageSize = 50

        // Give when api service returns failure
        coEvery { gitHubApiService.getRepoIssuesAsync(ownerName, repoName, pageSize, page) } returns CompletableDeferred(repoIssuesFailureResponse)

        // When load repo issues happens
        val actualResult = networkDataSource.loadRepoIssuesFromNetwork(ownerName, repoName, pageSize, page)

        // Then verify that api service is invoked only once
        // and returns expected result
        verify(exactly = 1) { gitHubApiService.getRepoIssuesAsync(ownerName, repoName, pageSize, page) }
        assertEquals(repoIssuesFailureResult.exception.message, (actualResult as Result.Error).exception.message)

    }

}