package com.monzo.androidtest.news.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.monzo.androidtest.core.di.providers.DataProvider
import com.monzo.androidtest.news.di.NewsModule
import com.monzo.androidtest.news.entities.NewsArticlesState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext


class NewsArticlesViewModel @Inject constructor(
        @param:Named(NewsModule.LOCAL_DATASOURCE) private val dataProvider: DataProvider<NewsArticlesState>
) : ViewModel(), CoroutineScope {

    private val job = Job()
    private val mutableLiveData: MutableLiveData<NewsArticlesViewState> = MutableLiveData()

    val newsArticlesViewState: LiveData<NewsArticlesViewState>
        get() = mutableLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init {
        loadNewsArticles()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    private fun loadNewsArticles() {
        launch(Dispatchers.IO) {
            dataProvider.requestData { item ->
                updateView(item)
            }
        }

    }

    private fun updateView(item: NewsArticlesState) =
            launch {
                withContext(Dispatchers.Main) {
                    mutableLiveData.value = when (item) {
                        NewsArticlesState.Loading -> NewsArticlesViewState.InProgress
                        is NewsArticlesState.Success -> NewsArticlesViewState.ShowNewsArticles(item.articles)
                        is NewsArticlesState.Error -> NewsArticlesViewState.ShowErrorMessage(item.error)
                    }
                }
            }
}
