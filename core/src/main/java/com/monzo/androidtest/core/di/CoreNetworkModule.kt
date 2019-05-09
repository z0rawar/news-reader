package com.monzo.androidtest.core.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
object CoreNetworkModule {

    @Provides
    @JvmStatic
    internal fun providesOkHttpClientBuilder(
    ): OkHttpClient.Builder =
            OkHttpClient.Builder()
                    .apply {
//                        loggingInterceptor?.also {
//                            addInterceptor(it)
//                        }
                    }
}