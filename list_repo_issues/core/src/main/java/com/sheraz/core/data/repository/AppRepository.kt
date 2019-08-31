package com.sheraz.core.data.repository

import androidx.lifecycle.LiveData
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity

interface AppRepository {

    val isFetchInProgress: LiveData<Boolean>
    val networkError: LiveData<Exception>

    fun getAllRepoIssuesLiveData(): LiveData<List<GitHubRepoIssueEntity>>
    fun getOpenIssuesLiveData(): LiveData<List<GitHubRepoIssueEntity>>
    fun getClosedIssuesLiveData(): LiveData<List<GitHubRepoIssueEntity>>
    suspend fun clearCache()
    suspend fun loadGitHubRepoIssuesList(ownerName: String = "", repoName: String = "", pageSize: Int = NETWORK_PAGE_SIZE, page: Int = 1)
    suspend fun refreshGitHubRepoIssuesList()

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}