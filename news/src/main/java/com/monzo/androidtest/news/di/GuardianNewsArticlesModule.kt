package com.monzo.androidtest.news.di

import com.monzo.androidtest.core.di.providers.DataMapper
import com.monzo.androidtest.core.di.providers.DataProvider
import com.monzo.androidtest.news.api.ApiArticleListResponse
import com.monzo.androidtest.news.api.ApiNewsArticlesProvider
import com.monzo.androidtest.news.api.Article
import com.monzo.androidtest.news.api.ArticleListDataMapper
import com.monzo.androidtest.news.api.GuardianService
import com.monzo.androidtest.news.entities.NewsArticlesState
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object GuardianNewsArticlesModule {

    @Provides
    @Named(NewsModule.REMOTE_DATASOURCE)
    @JvmStatic
    internal fun providesGuardianNewsProvider(
            apiService: GuardianService,
            mapper: DataMapper<ApiArticleListResponse, List<Article>>
    ): DataProvider<NewsArticlesState> = ApiNewsArticlesProvider(apiService, mapper)

    @Provides
    @JvmStatic
    internal fun providesArticlesApiResponseDataMapper(): DataMapper<ApiArticleListResponse, List<Article>> {
        return ArticleListDataMapper()
    }
}
