package com.sheraz.listgithubrepoissues.ui.models

interface GitHubRepoIssueItem {

    /**
     * Items to be shown in the UI
     * It's not a good practice to pass
     * around whole database objects
     * or list of objects, as these objects
     * certainly have lot of stuff we don't
     * want in the UI
     */

    val id: Int
    val issueId: Int
    val title: String
    val body: String
    val state: String
    val authorLogin: String
    val createdAt: String
    val comments: Int
    val repositoryUrl: String

}