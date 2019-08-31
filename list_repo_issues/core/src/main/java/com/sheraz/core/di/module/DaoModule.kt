package com.sheraz.core.di.module

import com.sheraz.core.data.db.GitHubRepoDatabase
import com.sheraz.core.data.db.dao.GitHubRepoIssueEntityDao
import dagger.Module
import dagger.Provides

/**
 * Module responsible for providing [GitHubRepoIssueEntityDao] instance
 */

@Module
class DaoModule {

    @Provides
    fun provideGitHubRepoEntityDao(database: GitHubRepoDatabase): GitHubRepoIssueEntityDao = database.gitHubRepoIssueEntityDao()

}