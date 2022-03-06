package com.sheraz.core.di.module

import com.sheraz.core.data.db.GitHubRepoDatabase
import com.sheraz.core.data.db.dao.GitHubRepoEntityDao
import com.sheraz.core.data.db.dao.GitHubRepoIssueEntityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module responsible for providing [GitHubRepoIssueEntityDao] &
 * [GitHubRepoEntityDao] instances
 */

@InstallIn(SingletonComponent::class)
@Module
class DaoModule {

    @Provides
    @Singleton
    fun provideGitHubRepoIssueEntityDao(database: GitHubRepoDatabase): GitHubRepoIssueEntityDao = database.gitHubRepoIssueEntityDao()

    @Provides
    @Singleton
    fun provideGitHubRepoEntityDao(database: GitHubRepoDatabase): GitHubRepoEntityDao = database.gitHubRepoEntityDao()

}