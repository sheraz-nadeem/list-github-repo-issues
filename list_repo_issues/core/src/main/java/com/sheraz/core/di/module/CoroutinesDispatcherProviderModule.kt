package com.sheraz.core.di.module

import com.sheraz.core.data.CoroutinesDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Module responsible for providing [CoroutinesDispatcherProvider] instance
 * that can be used in our ViewModels to mainly perform async tasks.
 */

@InstallIn(SingletonComponent::class)
@Module
class CoroutinesDispatcherProviderModule {

    @Provides
    @Singleton
    fun provideCoroutinesDispatcherProvider(): CoroutinesDispatcherProvider = CoroutinesDispatcherProvider(
        Dispatchers.Main,
        Dispatchers.IO,
        Dispatchers.Default
    )
}