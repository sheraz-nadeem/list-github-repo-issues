package com.sheraz.listgithubrepoissues.ui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubRepoItem(
    val id: Int,
    val repoId: Int,
    val fullName: String,
    val name: String,
    val isRepoPrivate: Boolean,
    val htmlUrl: String,
    val url: String,
    val description: String?,
    val fork: Boolean,
    val ownerAvatarUrl: String?,
    val ownerHtmlUrl: String,
    val ownerType: String?,
    val ownerLogin: String?
) : Parcelable {

    override fun equals(other: Any?): Boolean {

        val item = other as GitHubRepoItem
        return (item.id == this.id && item.repoId == this.repoId)

    }
}