package com.sheraz.core.di.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.sheraz.core.data.sharedprefs.AppSharedPrefs
import com.sheraz.core.data.db.GitHubRepoDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module responsible for providing [GitHubRepoDatabase] instance
 */

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): GitHubRepoDatabase = GitHubRepoDatabase.invoke(context)

    @Provides
    @Singleton
    fun provideDefaultSharedPrefs(context: Context): SharedPreferences
            = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    @Provides
    fun provideAppSharedPrefs(sharedPreferences: SharedPreferences): AppSharedPrefs = AppSharedPrefs.getInstance(sharedPreferences)
}