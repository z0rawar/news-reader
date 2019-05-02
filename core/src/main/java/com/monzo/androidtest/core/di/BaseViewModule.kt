package com.monzo.androidtest.core.di

import dagger.Binds
import dagger.Module
import javax.inject.Singleton
import androidx.lifecycle.ViewModelProvider

@Module
abstract class BaseViewModule {
 
    @Singleton
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory
}