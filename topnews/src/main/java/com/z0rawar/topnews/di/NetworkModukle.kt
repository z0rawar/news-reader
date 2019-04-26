package com.z0rawar.topnews.di

import com.google.gson.Gson
import com.z0rawar.core.di.CoreNetworkModule
import com.z0rawar.topnews.BuildConfig
import com.z0rawar.topnews.persistance.apiModel.GuardianService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [CoreNetworkModule::class])
object NetworkModukle{


    private val BASE_URL = "https://content.guardianapis.com"
    private val HEADER_API_KEY = "api-key"

    @Provides
    @Named("API_KEY")
    @JvmStatic
    internal fun providesApiKey() =
            Interceptor { chain ->
                val newRequest = chain.request().let { request ->
                    val newUrl = request.headers().newBuilder()
                            .add(HEADER_API_KEY, BuildConfig.GUARDIAN_API_KEY)
                            .build()
                    request.newBuilder()
                            .headers(newUrl)
                            .build()
                }
                chain.proceed(newRequest)
            }

    @Provides
    @JvmStatic
    internal fun providesOkHttpClient(
            builder: OkHttpClient.Builder,
            @Named("API_KEY") apiKeyInterceptor: Interceptor): OkHttpClient =
            builder.addInterceptor(apiKeyInterceptor)
                    .build()

    @Provides
    @Singleton
    @JvmStatic
    internal fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(Gson()))
                    .client(okHttpClient)
                    .build()

    @Provides
    @JvmStatic
    internal fun providesGuardianService(retrofit: Retrofit): GuardianService =
            retrofit.create(GuardianService::class.java)


}