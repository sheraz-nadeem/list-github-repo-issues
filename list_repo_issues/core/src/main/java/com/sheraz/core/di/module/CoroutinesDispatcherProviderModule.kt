package com.sheraz.core.di.module

import com.sheraz.core.data.CoroutinesDispatcherProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

/**
 * Module responsible for providing [CoroutinesDispatcherProvider] instance
 * that can be used in our ViewModels to mainly perform async tasks.
 */

@Module
class CoroutinesDispatcherProviderModule {

    @Provides
    fun provideCoroutinesDispatcherProvider(): CoroutinesDispatcherProvider = CoroutinesDispatcherProvider(
        Dispatchers.Main,
        Dispatchers.IO,
        Dispatchers.Default
    )
}