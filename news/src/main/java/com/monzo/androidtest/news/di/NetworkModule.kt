package com.monzo.androidtest.news.di

import com.monzo.androidtest.core.di.CoreNetworkModule
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


@Module(includes = [CoreNetworkModule::class])
object NetworkModule {

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
            builder: OkHttpClient.Builder,
            apiKeyInterceptor: Interceptor
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