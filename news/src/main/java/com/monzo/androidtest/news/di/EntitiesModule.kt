package com.monzo.androidtest.news.di

import com.monzo.androidtest.core.providers.DataPersister
import com.monzo.androidtest.core.providers.DataProvider
import com.monzo.androidtest.news.api.Article
import com.monzo.androidtest.news.providers.NewsArticlesRepository
import com.monzo.androidtest.news.entities.NewsArticlesState
import dagger.Module
import dagger.Provides
import javax.inject.Named


@Module
object EntitiesModule {

    @Provides
    @Named(NewsModule.LOCAL_DATASOURCE)
    @JvmStatic
    internal fun provideNewsArticlesRepository(
            dataPersister: DataPersister<List<Article>>,
            @Named(NewsModule.REMOTE_DATASOURCE) dataProvider: DataProvider<NewsArticlesState>
    ): DataProvider<NewsArticlesState> = NewsArticlesRepository(dataPersister, dataProvider)
}
