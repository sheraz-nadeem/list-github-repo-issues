package com.sheraz.listgithubrepoissues.di.component

import com.sheraz.core.di.component.CoreComponent
import com.sheraz.core.di.scope.FeatureScope
import com.sheraz.listgithubrepoissues.di.module.CoroutinesDispatcherProviderModule
import com.sheraz.listgithubrepoissues.di.module.HomeModule
import com.sheraz.listgithubrepoissues.di.module.ViewModelFactoryProviderModule
import com.sheraz.listgithubrepoissues.ui.modules.home.HomeViewModel
import com.sheraz.listgithubrepoissues.ui.modules.home.HomeViewModelFactory
import dagger.Component

@Component(
    modules = [
        HomeModule::class,
        CoroutinesDispatcherProviderModule::class,
        ViewModelFactoryProviderModule::class
    ],
    dependencies = [CoreComponent::class]
)
@FeatureScope
interface AppComponent {
    fun homeViewModel(): HomeViewModel
    fun homeViewModelFactory(): HomeViewModelFactory
}