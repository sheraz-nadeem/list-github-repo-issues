package com.sheraz.core.network.response

import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity

data class GetGitHubRepoIssuesResponse(

    val gitHubRepoIssueList: List<GitHubRepoIssueEntity>

)