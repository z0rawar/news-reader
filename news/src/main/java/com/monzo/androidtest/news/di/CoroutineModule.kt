package com.monzo.androidtest.news.di

import com.monzo.androidtest.news.di.NewsModule.Companion.IO_CONTEXT
import com.monzo.androidtest.news.di.NewsModule.Companion.UI_CONTEXT
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

@Module
object CoroutineModule {


    @Provides
    @JvmStatic
    @Named(UI_CONTEXT)
    internal fun provideUICoroutineContext() : CoroutineContext = Dispatchers.Main

    @Provides
    @JvmStatic
    @Named(IO_CONTEXT)
    internal fun provideIOCoroutineContext() : CoroutineContext = Dispatchers.IO

}
