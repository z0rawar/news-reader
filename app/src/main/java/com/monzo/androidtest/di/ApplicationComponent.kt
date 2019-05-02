package com.monzo.androidtest.di

import com.monzo.androidtest.HeadlinesApp
import com.monzo.androidtest.news.di.NewsModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ApplicationModule::class,
    ActivityModule::class,
    NewsModule::class
])
interface ApplicationComponent : AndroidInjector<HeadlinesApp>{

    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<HeadlinesApp>()

}


