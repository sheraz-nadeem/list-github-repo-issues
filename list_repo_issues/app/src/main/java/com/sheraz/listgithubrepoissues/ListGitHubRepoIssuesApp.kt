package com.sheraz.listgithubrepoissues

import android.app.Application

class ListGitHubRepoIssuesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {

        private val TAG = ListGitHubRepoIssuesApp::class.java.simpleName
        private var INSTANCE: ListGitHubRepoIssuesApp? = null

        fun get(): ListGitHubRepoIssuesApp = INSTANCE!!

    }
}