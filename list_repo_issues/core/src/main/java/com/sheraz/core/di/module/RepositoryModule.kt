package com.sheraz.core.di.module

import com.sheraz.core.data.db.dao.GitHubRepoIssueEntityDao
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.core.data.repository.AppRepositoryImpl
import com.sheraz.core.network.GitHubNetworkDataSource
import com.sheraz.core.utils.Logger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAppRepository(
        logger: Logger,
        dao: GitHubRepoIssueEntityDao,
        networkDataSource: GitHubNetworkDataSource
    ): AppRepository = AppRepositoryImpl.invoke(logger, dao, networkDataSource)

}