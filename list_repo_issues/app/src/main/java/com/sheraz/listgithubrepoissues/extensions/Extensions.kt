package com.sheraz.listgithubrepoissues.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoIssueItem

/**
 * A Kotlin Extension function for converting [GitHubRepoIssueEntity]
 * object into [GitHubRepoIssueItem]
 */

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
        comments = comments,
        authorAvatarUrl = user?.avatarUrl,
        authorAssociation = authorAssociation
    )
}

/**
 * An Extension function for AppCompatActivity to lazily initialize
 * ViewModel instance by providing [ViewModelProvider.Factory] instance
 */

inline fun <reified V : ViewModel> AppCompatActivity.bindViewModel(viewModelFactory: ViewModelProvider.Factory) =
    lazy { ViewModelProviders.of(this, viewModelFactory).get(V::class.java) }