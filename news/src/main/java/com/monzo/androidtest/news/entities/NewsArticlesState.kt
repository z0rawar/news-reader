package com.monzo.androidtest.news.entities

import com.monzo.androidtest.news.api.Article

sealed class NewsArticlesState{

    object Loading : NewsArticlesState()

    class Success(val articles: List<Article>) : NewsArticlesState()

    class Error(val error : String) : NewsArticlesState()
}