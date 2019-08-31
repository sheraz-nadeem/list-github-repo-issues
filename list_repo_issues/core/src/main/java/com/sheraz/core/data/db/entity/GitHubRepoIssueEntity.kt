package com.sheraz.core.data.db.entity


import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "github_repo_issue", indices = [Index(value = ["issueId"], unique = true)])
data class GitHubRepoIssueEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val rowId: Int,

    @ColumnInfo(name = "issueId")
    @SerializedName("id")
    val issueId: Int,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,

    @ColumnInfo(name = "body")
    @SerializedName("body")
    val body: String,

    @ColumnInfo(name = "state")
    @SerializedName("state")
    val state: String,

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    val createdAt: String,

    @ColumnInfo(name = "updated_at")
    @SerializedName("updated_at")
    val updatedAt: String,

    @ColumnInfo(name = "author_association")
    @SerializedName("author_association")
    val authorAssociation: String,

    @ColumnInfo(name = "closed_at")
    @SerializedName("closed_at")
    val closedAt: String?,

    @ColumnInfo(name = "comments")
    @SerializedName("comments")
    val comments: Int,

    @ColumnInfo(name = "comments_url")
    @SerializedName("comments_url")
    val commentsUrl: String,

    @ColumnInfo(name = "events_url")
    @SerializedName("events_url")
    val eventsUrl: String,

    @ColumnInfo(name = "html_url")
    @SerializedName("html_url")
    val htmlUrl: String,

    @ColumnInfo(name = "labels_url")
    @SerializedName("labels_url")
    val labelsUrl: String,

    @ColumnInfo(name = "locked")
    @SerializedName("locked")
    val locked: Boolean,

    @ColumnInfo(name = "node_id")
    @SerializedName("node_id")
    val nodeId: String,

    @ColumnInfo(name = "number")
    @SerializedName("number")
    val number: Int,

    @ColumnInfo(name = "repository_url")
    @SerializedName("repository_url")
    val repositoryUrl: String,

    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String,

    @Embedded(prefix = "user_")
    @SerializedName("user")
    val user: User

) {

    data class User(

        @ColumnInfo(name = "id")
        @SerializedName("id")
        val id: Int,

        @ColumnInfo(name = "login")
        @SerializedName("login")
        val login: String,

        @ColumnInfo(name = "avatar_url")
        @SerializedName("avatar_url")
        val avatarUrl: String,

        @ColumnInfo(name = "events_url")
        @SerializedName("events_url")
        val eventsUrl: String,

        @ColumnInfo(name = "followers_url")
        @SerializedName("followers_url")
        val followersUrl: String,

        @ColumnInfo(name = "following_url")
        @SerializedName("following_url")
        val followingUrl: String,

        @ColumnInfo(name = "gists_url")
        @SerializedName("gists_url")
        val gistsUrl: String,

        @ColumnInfo(name = "gravatar_id")
        @SerializedName("gravatar_id")
        val gravatarId: String,

        @ColumnInfo(name = "html_url")
        @SerializedName("html_url")
        val htmlUrl: String,

        @ColumnInfo(name = "node_id")
        @SerializedName("node_id")
        val nodeId: String,

        @ColumnInfo(name = "organizations_url")
        @SerializedName("organizations_url")
        val organizationsUrl: String,

        @ColumnInfo(name = "received_events_url")
        @SerializedName("received_events_url")
        val receivedEventsUrl: String,

        @ColumnInfo(name = "repos_url")
        @SerializedName("repos_url")
        val reposUrl: String,

        @ColumnInfo(name = "site_admin")
        @SerializedName("site_admin")
        val siteAdmin: Boolean,

        @ColumnInfo(name = "starred_url")
        @SerializedName("starred_url")
        val starredUrl: String,

        @ColumnInfo(name = "subscriptions_url")
        @SerializedName("subscriptions_url")
        val subscriptionsUrl: String,

        @ColumnInfo(name = "type")
        @SerializedName("type")
        val type: String,

        @ColumnInfo(name = "url")
        @SerializedName("url")
        val url: String
    )

}