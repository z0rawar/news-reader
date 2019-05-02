package com.monzo.androidtest.news.view

import android.util.Log
import androidx.lifecycle.ViewModel
import com.monzo.androidtest.core.di.providers.DataProvider
import com.monzo.androidtest.news.di.NewsModule
import com.monzo.androidtest.news.entities.NewsArticlesState
import javax.inject.Inject
import javax.inject.Named


class NewsArticlesViewModel @Inject constructor(
        @param:Named(NewsModule.LOCAL_DATASOURCE) private val dataProvider: DataProvider<NewsArticlesState>
) : ViewModel() {

    init {
        loadNewsArticles()
    }

    private fun loadNewsArticles() {
//TODO Wrap in coroutines
        dataProvider.requestData {
            Log.d("NewsArticlesViewModel", it.toString())
        }
    }
}
