package com.monzo.androidtest.di

import android.app.Application
import com.monzo.androidtest.HeadlinesApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApplicationModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideApplication(app: HeadlinesApp) : Application = app
}
