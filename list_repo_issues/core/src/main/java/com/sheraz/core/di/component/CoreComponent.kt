package com.sheraz.core.di.component

import android.content.Context
import com.sheraz.core.data.db.GitHubRepoDatabase
import com.sheraz.core.data.db.dao.GitHubRepoIssueEntityDao
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.core.di.module.*
import com.sheraz.core.network.GitHubApiService
import com.sheraz.core.network.GitHubNetworkDataSource
import com.sheraz.core.utils.Logger
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * A Dagger Component that is responsible for
 * providing core/data dependencies
 */

@Singleton
@Component(
    modules = [
        ContextModule::class,
        DaoModule::class,
        DatabaseModule::class,
        LoggerModule::class,
        NetworkModule::class,
        RepositoryModule::class
    ]
)
interface CoreComponent {
    fun appContext(): Context
    fun database(): GitHubRepoDatabase
    fun gitHubRepoEntityDao(): GitHubRepoIssueEntityDao
    fun retrofit(): Retrofit
    fun gitHubRepoApiService(): GitHubApiService
    fun gitHubNetworkDataSource(): GitHubNetworkDataSource
    fun appRepository(): AppRepository
    fun logger(): Logger
}