package com.monzo.androidtest.news.di

import com.monzo.androidtest.core.providers.DataMapper
import com.monzo.androidtest.core.providers.DataProvider
import com.monzo.androidtest.news.api.ApiArticle
import com.monzo.androidtest.news.api.ApiArticleListResponse
import com.monzo.androidtest.news.providers.ApiNewsArticlesProvider
import com.monzo.androidtest.news.api.Article
import com.monzo.androidtest.news.mapper.ArticleDataMapper
import com.monzo.androidtest.news.mapper.ArticleListDataMapper
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
    internal fun providesArticlesApiDataMapper(): DataMapper<ApiArticle, List<Article>> {
        return ArticleDataMapper()
    }
    @Provides
    @JvmStatic
    internal fun providesArticlesApiResponseDataMapper(articleDataMapper: DataMapper<ApiArticle, List<Article>>):
            DataMapper<ApiArticleListResponse,
                    List<Article>> {
        return ArticleListDataMapper(articleDataMapper)
    }
}
