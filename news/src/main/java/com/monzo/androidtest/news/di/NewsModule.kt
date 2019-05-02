package com.monzo.androidtest.news.di

import androidx.lifecycle.ViewModel
import com.monzo.androidtest.core.di.BaseViewModule
import com.monzo.androidtest.core.di.ViewModelKey
import com.monzo.androidtest.news.view.NewsArticlesViewModel
import com.monzo.androidtest.news.view.NewsListFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module(includes = [
    EntitiesModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    BaseViewModule::class,
    GuardianNewsArticlesModule::class
])
abstract class NewsModule {

    companion object {
        const val REMOTE_DATASOURCE = "REMOTE"
        const val LOCAL_DATASOURCE = "LOCAL"
        const val GUARDIAN_API_KEY = "enj8pstqu5yat6yesfsdmd39"
        const val HEADER_API_KEY = "api-key"
    }

    @ContributesAndroidInjector
    abstract fun bindsNewsListFragment(): NewsListFragment

    @Binds
    @IntoMap
    @ViewModelKey(NewsArticlesViewModel::class)
    abstract fun bindNewsArticlesViewModel(newsArticlesViewModel: NewsArticlesViewModel): ViewModel

}