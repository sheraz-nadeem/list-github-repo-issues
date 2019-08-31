package com.sheraz.core.di.module

import com.sheraz.core.utils.Logger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module responsible for providing [Logger] instance
 */

@Module
class LoggerModule {

    @Provides
    @Singleton
    fun provideLogger() = Logger()
}