package com.monzo.androidtest.news.view

import android.util.Log
import androidx.lifecycle.ViewModel
import com.monzo.androidtest.core.di.providers.DataProvider
import com.monzo.androidtest.news.di.NewsModule
import com.monzo.androidtest.news.entities.NewsArticlesState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class NewsArticlesViewModel @Inject constructor(
        @param:Named(NewsModule.LOCAL_DATASOURCE) private val dataProvider: DataProvider<NewsArticlesState>
) : ViewModel(), CoroutineScope {


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    init {
        loadNewsArticles()
    }

    private fun loadNewsArticles() {
        launch(Dispatchers.IO){
            dataProvider.requestData {
                Log.d("NewsArticlesViewModel", it.toString())
            }
        }

    }
}
