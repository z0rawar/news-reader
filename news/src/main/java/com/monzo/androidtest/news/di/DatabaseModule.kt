package com.monzo.androidtest.news.di

import android.app.Application
import androidx.room.Room
import com.monzo.androidtest.core.di.providers.DataMapper
import com.monzo.androidtest.core.di.providers.DataPersister
import com.monzo.androidtest.news.api.Article
import com.monzo.androidtest.news.data.ArticlesDao
import com.monzo.androidtest.news.data.DatabaseArticlesMapper
import com.monzo.androidtest.news.data.DatabaseNewsArticlesPersister
import com.monzo.androidtest.news.data.NewsArticle
import com.monzo.androidtest.news.data.NewsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    @JvmStatic
    internal fun providesDatabase(context: Application): NewsDatabase =
            Room.databaseBuilder(context, NewsDatabase::class.java, "news")
                    .fallbackToDestructiveMigration()
                    .build()

    @Provides
    @Singleton
    @JvmStatic
    internal fun providesArticlesDao(database: NewsDatabase): ArticlesDao =
            database.articlesDao()

    @Provides
    @JvmStatic
    internal fun providesDatabaseNewsArticlesMapper(): DataMapper<Article, NewsArticle> =
            DatabaseArticlesMapper()


    @Provides
    @JvmStatic
    internal fun providesDatabasePersister(
            dao: ArticlesDao,
            mapper: DataMapper<Article, NewsArticle>
    ): DataPersister<List<Article>> =
            DatabaseNewsArticlesPersister(dao, mapper)

}
