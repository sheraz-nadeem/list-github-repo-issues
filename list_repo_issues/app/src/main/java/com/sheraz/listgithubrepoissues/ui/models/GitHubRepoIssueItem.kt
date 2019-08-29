package com.sheraz.listgithubrepoissues.ui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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
    val repositoryUrl: String
) : Parcelable {

    override fun equals(other: Any?): Boolean {

        val item = other as GitHubRepoIssueItem
        return (item.id == this.id && item.issueId == this.issueId)

    }
}