package com.monzo.androidtest.di

import com.monzo.androidtest.HeadlinesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindHeadlinesActivity() : HeadlinesActivity
}
