package com.sheraz.core.di.module

import android.content.Context
import com.sheraz.core.data.db.GitHubRepoDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): GitHubRepoDatabase = GitHubRepoDatabase.invoke(context)

}