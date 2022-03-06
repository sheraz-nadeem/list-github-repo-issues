package com.sheraz.core.di.module

import com.sheraz.core.utils.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module responsible for providing [Logger] instance
 */

@InstallIn(SingletonComponent::class)
@Module
class LoggerModule {

    @Provides
    @Singleton
    fun provideLogger() = Logger()
}