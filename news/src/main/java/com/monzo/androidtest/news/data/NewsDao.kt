package com.monzo.androidtest.news.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllArticles(articles: List<NewsArticle>)

    @Query("SELECT * FROM NewsArticle")
    abstract fun getAllArticles() : List<NewsArticle>


}
