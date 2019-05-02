package com.monzo.androidtest.news.di

import com.monzo.androidtest.core.di.CoreNetworkModule
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
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [CoreNetworkModule::class])
object NetworkModule {

    @Provides
    @Named("API_KEY")
    @JvmStatic
    internal fun providesApiKey() =
            Interceptor { chain ->
                val newRequest = chain.request().let { request ->
                    val newUrl = request.url().newBuilder()
                            .addQueryParameter("api_key", "enj8pstqu5yat6yesfsdmd39")
                            .build()
                    request.newBuilder()
                            .url(newUrl)
                            .build()
                }
                chain.proceed(newRequest)
            }

    @Provides
    @JvmStatic
    internal fun providesOkHttpClient(
            builder: OkHttpClient.Builder,
            @Named("API_KEY") apiKeyInterceptor: Interceptor
    ): OkHttpClient =
            builder.addInterceptor(apiKeyInterceptor)
                    .build()

    @Provides
    @Singleton
    @JvmStatic
    internal fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .baseUrl("https://content.guardianapis.com")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    @Provides
    @JvmStatic
    internal fun providesGuardianService(retrofit: Retrofit): GuardianService =
            retrofit.create(GuardianService::class.java)
}