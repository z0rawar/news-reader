package com.monzo.androidtest.news.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsArticle::class],
        version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao
}