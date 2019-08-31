package com.sheraz.listgithubrepoissues.di

import com.sheraz.core.di.component.CoreComponent
import com.sheraz.listgithubrepoissues.ListGitHubRepoIssuesApp
import com.sheraz.listgithubrepoissues.di.component.AppComponent

/**
 * A public class that cannot be instantiated and used
 * solely for the purpose of providing [AppComponent]
 * & [CoreComponent] instances for Dependency Injection
 */

class Injector private constructor() {

    companion object {
        fun getAppComponent(): AppComponent = ListGitHubRepoIssuesApp.get().appComponent
        fun getCoreComponent(): CoreComponent = ListGitHubRepoIssuesApp.get().coreComponent
    }

}