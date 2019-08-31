package com.sheraz.listgithubrepoissues.di.module

import com.sheraz.core.data.repository.AppRepository
import com.sheraz.core.utils.Logger
import com.sheraz.listgithubrepoissues.ui.modules.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides


/**
 * Module responsible for providing All kinds of ViewModelFactory with args.
 */

@Module
class ViewModelFactoryProviderModule {

    @Provides
    fun provideHomeViewModelFactory(logger: Logger, appRepository: AppRepository): HomeViewModelFactory {
        return HomeViewModelFactory(logger, appRepository)
    }

}