package com.sheraz.listgithubrepoissues

import android.app.Application
import com.sheraz.core.di.component.CoreComponent
import com.sheraz.core.di.component.DaggerCoreComponent
import com.sheraz.core.di.module.ContextModule
import com.sheraz.listgithubrepoissues.di.component.AppComponent
import com.sheraz.listgithubrepoissues.di.component.DaggerAppComponent

class ListGitHubRepoIssuesApp : Application() {

    lateinit var appComponent: AppComponent
        private set
    lateinit var coreComponent: CoreComponent
        private set

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        coreComponent = DaggerCoreComponent.builder()
            .contextModule(ContextModule(this))
            .build()
        appComponent = DaggerAppComponent.builder()
            .coreComponent(coreComponent)
            .build()

        coreComponent.logger().d(TAG, "onCreate: ")
    }

    companion object {
        private val TAG = ListGitHubRepoIssuesApp::class.java.simpleName

        private var INSTANCE: ListGitHubRepoIssuesApp? = null

        fun get(): ListGitHubRepoIssuesApp = INSTANCE!!
    }
}