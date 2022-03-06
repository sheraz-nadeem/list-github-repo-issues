package com.sheraz.core.di.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.sheraz.core.data.sharedprefs.AppSharedPrefs
import com.sheraz.core.data.db.GitHubRepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module responsible for providing [GitHubRepoDatabase] instance
 */

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): GitHubRepoDatabase = GitHubRepoDatabase.invoke(context)

    @Provides
    @Singleton
    fun provideDefaultSharedPrefs(
        @ApplicationContext context: Context
    ): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

}