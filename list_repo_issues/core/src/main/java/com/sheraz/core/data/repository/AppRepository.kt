package com.sheraz.core.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.sheraz.core.data.db.entity.GitHubRepoEntity
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity

/**
 * An interface that is responsible to provide methods for
 * interacting with different data sources like NetworkDataSource
 * & Database/Local Cache.
 *
 * Following the principle of `Programming to an interface`,
 * this interface is used to provide methods that any
 * implementation class must provide in order for other
 * application components to work seamlessly.
 */

interface AppRepository {

    val isFetchInProgress: LiveData<Boolean>
    val networkError: LiveData<Exception>
    val noMoreItemsAvailable: LiveData<Boolean>

    fun getAllRepoIssuesPagedFactory(): DataSource.Factory<Int, GitHubRepoIssueEntity>
    fun getAllReposPagedFactory(): DataSource.Factory<Int, GitHubRepoEntity>

    fun getAllReposLiveData(): LiveData<List<GitHubRepoEntity>>
    fun getAllRepoIssuesLiveData(): LiveData<List<GitHubRepoIssueEntity>>
    fun getOpenIssuesLiveData(): LiveData<List<GitHubRepoIssueEntity>>
    fun getClosedIssuesLiveData(): LiveData<List<GitHubRepoIssueEntity>>

    suspend fun clearRepoIssuesCache()
    suspend fun clearReposCache()
    suspend fun loadGitHubRepoIssuesList(resetMoreItemsAvailable: Boolean = false, ownerName: String = "", repoName: String = "", pageSize: Int = NETWORK_PAGE_SIZE, page: Int = 1)
    suspend fun loadGitHubReposList(resetMoreItemsAvailable: Boolean = false, query: String = "", pageSize: Int = NETWORK_PAGE_SIZE, page: Int = 1)
//    suspend fun refreshGitHubRepoIssuesList(ownerName: String, repoName: String)

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}