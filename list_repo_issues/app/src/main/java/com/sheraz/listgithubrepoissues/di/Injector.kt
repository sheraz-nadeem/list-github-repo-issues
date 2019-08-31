package com.sheraz.listgithubrepoissues.di

import com.sheraz.core.di.component.CoreComponent
import com.sheraz.listgithubrepoissues.ListGitHubRepoIssuesApp
import com.sheraz.listgithubrepoissues.di.component.AppComponent

class Injector private constructor() {

    companion object {
        fun getAppComponent(): AppComponent = ListGitHubRepoIssuesApp.get().appComponent
        fun getCoreComponent(): CoreComponent = ListGitHubRepoIssuesApp.get().coreComponent
    }

}