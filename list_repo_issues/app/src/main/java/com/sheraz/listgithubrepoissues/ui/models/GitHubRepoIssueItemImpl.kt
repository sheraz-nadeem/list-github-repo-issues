package com.sheraz.listgithubrepoissues.ui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubRepoIssueItemImpl(
    override val id: Int,
    override val issueId: Int,
    override val title: String,
    override val body: String,
    override val state: String,
    override val authorLogin: String,
    override val createdAt: String,
    override val comments: Int,
    override val repositoryUrl: String
) : GitHubRepoIssueItem, Parcelable {

    override fun equals(other: Any?): Boolean {

        val item = other as GitHubRepoIssueItemImpl
        return (item.id == this.id && item.issueId == this.issueId)

    }
}