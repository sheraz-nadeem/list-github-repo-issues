package com.sheraz.core.di.module

import com.sheraz.core.data.db.datasourcefactory.GitHubRepoEntityDataSourceFactory
import com.sheraz.core.data.db.dao.GitHubRepoEntityDao
import com.sheraz.core.data.db.dao.GitHubRepoIssueEntityDao
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.core.data.repository.AppRepositoryImpl
import com.sheraz.core.network.GitHubNetworkDataSource
import com.sheraz.core.utils.Logger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module responsible for providing [AppRepository] instance
 * This module will provide more instances if any other
 * repository are created in future.
 */

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAppRepository(
        logger: Logger,
        gitHubRepoIssueEntityDao: GitHubRepoIssueEntityDao,
        gitHubRepoEntityDao: GitHubRepoEntityDao,
        gitHubRepoEntityDataSourceFactory: GitHubRepoEntityDataSourceFactory,
        networkDataSource: GitHubNetworkDataSource
    ): AppRepository = AppRepositoryImpl.invoke(logger, gitHubRepoIssueEntityDao, gitHubRepoEntityDao, gitHubRepoEntityDataSourceFactory, networkDataSource)

    @Provides
    fun provideGitHubRepoEntityDataSourceFactory(
        logger: Logger,
        gitHubRepoEntityDao: GitHubRepoEntityDao
    ): GitHubRepoEntityDataSourceFactory = GitHubRepoEntityDataSourceFactory(logger, gitHubRepoEntityDao)

}