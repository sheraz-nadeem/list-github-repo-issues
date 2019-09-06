package com.sheraz.listgithubrepoissues.ui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A UI model class that will be used to render
 * data in our views.
 */

@Parcelize
data class GitHubRepoIssueItem(
    val id: Int,
    val issueId: Int,
    val title: String,
    val body: String,
    val state: String,
    val authorLogin: String,
    val createdAt: String,
    val comments: Int,
    val repositoryUrl: String,
    val authorAvatarUrl: String,
    val authorAssociation: String
) : Parcelable {

    override fun equals(other: Any?): Boolean {

        val item = other as GitHubRepoIssueItem
        return (item.id == this.id && item.issueId == this.issueId)

    }

    override fun toString(): String {
        return "GitHubRepoItem => {\n" +
                "id = ${this.id}, \n" +
                "issueId = ${this.issueId}, \n" +
                "title = ${this.title}, \n" +
                "body = ${this.body}, \n" +
                "state = ${this.state}, \n" +
                "authorLogin = ${this.authorLogin}, \n" +
                "createdAt = ${this.createdAt}, \n" +
                "comments = ${this.comments}, \n" +
                "repositoryUrl = ${this.repositoryUrl}, \n" +
                "authorAvatarUrl = ${this.authorAvatarUrl}, \n" +
                "authorAssociation = ${this.authorAssociation} \n" +
                "}"
    }
}