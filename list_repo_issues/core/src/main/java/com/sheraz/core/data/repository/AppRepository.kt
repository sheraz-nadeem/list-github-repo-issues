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
    suspend fun fetchGitHubRepoIssuesList(repoFullName: String, page: Int, per_page: Int)
    suspend fun refreshGitHubRepoIssuesList()

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}