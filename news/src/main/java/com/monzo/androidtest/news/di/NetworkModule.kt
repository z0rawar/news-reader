package com.monzo.androidtest.news.di

import com.monzo.androidtest.news.api.GuardianService
import com.monzo.androidtest.news.di.NewsModule.Companion.GUARDIAN_API_KEY
import com.monzo.androidtest.news.di.NewsModule.Companion.HEADER_API_KEY
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
object NetworkModule {

    const val BASE_URL = "https://content.guardianapis.com"

    @Provides
    @JvmStatic
    internal fun getAuthInterceptor(): Interceptor =
            Interceptor { chain ->
                val original = chain.request()
                val hb = original.headers().newBuilder()
                hb.add(HEADER_API_KEY, GUARDIAN_API_KEY)
                chain.proceed(original.newBuilder().headers(hb.build()).build())
            }


    @Provides
    @JvmStatic
    internal fun providesOkHttpClient(
            apiKeyInterceptor: Interceptor
    ): OkHttpClient =
            OkHttpClient.Builder().addInterceptor(apiKeyInterceptor)
                    .build()

    @Provides
    @Singleton
    @JvmStatic
    internal fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    @Provides
    @JvmStatic
    internal fun providesGuardianService(retrofit: Retrofit): GuardianService =
            retrofit.create(GuardianService::class.java)
}