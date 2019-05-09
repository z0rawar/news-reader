package com.monzo.androidtest.news.view

sealed class NewsArticlesViewState{

    object InProgress: NewsArticlesViewState()

    class ShowNewsArticles(val newsArticles: List<Any>) : NewsArticlesViewState()

    class ShowErrorMessage(val errorMessage: String) : NewsArticlesViewState()
}