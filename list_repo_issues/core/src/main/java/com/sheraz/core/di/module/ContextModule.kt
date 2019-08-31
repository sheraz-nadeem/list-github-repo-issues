package com.sheraz.core.di.module

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Module responsible for providing [android.content.Context] instance
 */

@Module
class ContextModule(private val appContext: Context) {

    @Provides
    fun appContext(): Context = appContext

}