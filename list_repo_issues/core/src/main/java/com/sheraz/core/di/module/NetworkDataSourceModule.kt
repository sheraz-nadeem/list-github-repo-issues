package com.sheraz.core.di.module

import com.sheraz.core.network.GitHubNetworkDataSource
import com.sheraz.core.network.GitHubNetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindNetworkDataSource(gitHubNetworkDataSourceImpl: GitHubNetworkDataSourceImpl): GitHubNetworkDataSource

}