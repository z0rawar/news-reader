package com.monzo.androidtest.news.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.lifecycle.LiveData

@Dao
abstract class ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertAllArticles(articles: List<NewsArticle>)

    @Query("SELECT * FROM NewsArticle")
    abstract fun getAllArticlesLiveData(): LiveData<List<NewsArticle>>

    @Query("SELECT * FROM NewsArticle")
    abstract fun getAllArticles(): List<NewsArticle>

    @Query("SELECT * FROM NewsArticle WHERE id = :articleId")
    abstract fun getArticleById(articleId: String): NewsArticle

    @Update
    abstract fun updateArticle(newsArticle: NewsArticle)

}
