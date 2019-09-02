package com.sheraz.core.di.module

import com.sheraz.core.data.db.GitHubRepoDatabase
import com.sheraz.core.data.db.dao.GitHubRepoEntityDao
import com.sheraz.core.data.db.dao.GitHubRepoIssueEntityDao
import dagger.Module
import dagger.Provides

/**
 * Module responsible for providing [GitHubRepoIssueEntityDao] &
 * [GitHubRepoEntityDao] instances
 */

@Module
class DaoModule {

    @Provides
    fun provideGitHubRepoIssueEntityDao(database: GitHubRepoDatabase): GitHubRepoIssueEntityDao = database.gitHubRepoIssueEntityDao()

    @Provides
    fun provideGitHubRepoEntityDao(database: GitHubRepoDatabase): GitHubRepoEntityDao = database.gitHubRepoEntityDao()

}