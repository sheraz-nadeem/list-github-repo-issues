package com.sheraz.listgithubrepoissues

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ListGitHubRepoIssuesApp : Application() {

//    lateinit var appComponent: AppComponent
//        private set
//    lateinit var coreComponent: CoreComponent
//        private set

    override fun onCreate() {
        super.onCreate()
//        INSTANCE = this
//        coreComponent = DaggerCoreComponent.builder()
//            .contextModule(ContextModule(this))
//            .build()
//        appComponent = DaggerAppComponent.builder()
//            .coreComponent(coreComponent)
//            .build()
//
//        coreComponent.logger().d(TAG, "onCreate: ")
    }

//    companion object {
//        private val TAG = ListGitHubRepoIssuesApp::class.java.simpleName
//
//        private var INSTANCE: ListGitHubRepoIssuesApp? = null
//
//        fun get(): ListGitHubRepoIssuesApp = INSTANCE!!
//    }
}