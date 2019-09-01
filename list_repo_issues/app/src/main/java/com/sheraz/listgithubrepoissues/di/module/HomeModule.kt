package com.sheraz.listgithubrepoissues.di.module

import com.sheraz.core.data.repository.AppRepository
import com.sheraz.core.utils.Logger
import com.sheraz.listgithubrepoissues.ui.modules.adapters.HomeAdapter
import com.sheraz.listgithubrepoissues.ui.modules.home.HomeViewModel
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

/**
 * Module responsible for providing [HomeViewModel] instance
 */

@Module
class HomeModule {

    @Provides
    fun provideHomeViewModel(appRepository: AppRepository): HomeViewModel {
        return HomeViewModel(appRepository)
    }

    @Provides
    fun provideHomeAdapter(logger: Logger, picasso: Picasso): HomeAdapter = HomeAdapter(logger, picasso)

}