package com.sheraz.listgithubrepoissues.extensions

import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoIssueItem

fun GitHubRepoIssueEntity.toUiModel(): GitHubRepoIssueItem {
    return GitHubRepoIssueItem(
        id = rowId,
        issueId = issueId,
        title = title,
        body = body,
        state = state,
        authorLogin = user?.login,
        createdAt = createdAt,
        repositoryUrl = repositoryUrl,
        comments = comments
    )
}