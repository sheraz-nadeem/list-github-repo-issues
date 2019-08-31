package com.sheraz.listgithubrepoissues.di.module

import com.sheraz.core.data.repository.AppRepository
import com.sheraz.listgithubrepoissues.ui.modules.home.HomeViewModel
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    fun provideHomeViewModel(appRepository: AppRepository): HomeViewModel {
        return HomeViewModel(appRepository)
    }

}