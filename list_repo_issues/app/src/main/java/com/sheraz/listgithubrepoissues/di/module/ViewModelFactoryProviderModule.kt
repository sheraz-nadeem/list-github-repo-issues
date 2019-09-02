package com.sheraz.listgithubrepoissues.di.module

import com.sheraz.core.data.repository.AppRepository
import com.sheraz.core.data.sharedprefs.AppSharedPrefs
import com.sheraz.core.utils.Logger
import com.sheraz.listgithubrepoissues.ui.modules.home.HomeViewModelFactory
import com.sheraz.listgithubrepoissues.ui.modules.home.searchrepo.SearchRepoViewModelFactory
import dagger.Module
import dagger.Provides


/**
 * Module responsible for providing All kinds of ViewModelFactory with args.
 */

@Module
class ViewModelFactoryProviderModule {

    @Provides
    fun provideHomeViewModelFactory(logger: Logger, appRepository: AppRepository, appSharedPrefs: AppSharedPrefs): HomeViewModelFactory {
        return HomeViewModelFactory(logger, appRepository, appSharedPrefs)
    }

    @Provides
    fun provideSearchRepoViewModelFactory(logger: Logger, appRepository: AppRepository): SearchRepoViewModelFactory {
        return SearchRepoViewModelFactory(logger, appRepository)
    }

}