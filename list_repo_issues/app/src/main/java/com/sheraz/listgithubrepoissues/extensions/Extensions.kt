package com.sheraz.listgithubrepoissues.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.sheraz.core.data.db.entity.GitHubRepoEntity
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoIssueItem
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoItem

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
 * A Kotlin Extension function for converting [GitHubRepoEntity]
 * object into [GitHubRepoItem]
 */

fun GitHubRepoEntity.toUiModel(): GitHubRepoItem {
    return GitHubRepoItem(
        id = rowId,
        repoId = repoId,
        fullName = fullName,
        name = name,
        isRepoPrivate = isRepoPrivate,
        htmlUrl = htmlUrl,
        url = url,
        description = description,
        fork = fork,
        ownerAvatarUrl = owner.avatarUrl,
        ownerHtmlUrl = owner.htmlUrl,
        ownerType = owner.type,
        ownerLogin = owner.login
    )
}


/**
 * An Extension function for AppCompatActivity & Fragment to lazily initialize
 * ViewModel instance by providing [ViewModelProvider.Factory] instance
 */

inline fun <reified V : ViewModel> AppCompatActivity.bindViewModel(viewModelFactory: ViewModelProvider.Factory) =
    lazy { ViewModelProviders.of(this, viewModelFactory).get(V::class.java) }

inline fun <reified V : ViewModel> Fragment.bindViewModel(viewModelFactory: ViewModelProvider.Factory) =
    lazy { ViewModelProviders.of(this, viewModelFactory).get(V::class.java) }



/**
 * An Extension function for AppCompatActivity to find a fragment by tag
 */

inline fun <reified T : Fragment> AppCompatActivity.findFragmentByTag(tag: String) : T? = supportFragmentManager.findFragmentByTag(tag) as? T
