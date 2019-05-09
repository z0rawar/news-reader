package com.monzo.androidtest.news.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.monzo.androidtest.core.di.providers.DataProvider
import com.monzo.androidtest.news.api.Article
import com.monzo.androidtest.news.di.NewsModule
import com.monzo.androidtest.news.entities.NewsArticlesState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext


class NewsArticlesViewModel @Inject constructor(
        @param:Named(NewsModule.LOCAL_DATASOURCE) private val dataProvider: DataProvider<NewsArticlesState>
) : ViewModel(), CoroutineScope {

    private var mediatorLiveData: MediatorLiveData<NewsArticlesViewState> = MediatorLiveData()
    val newsArticlesViewState: LiveData<NewsArticlesViewState>
        get() = mediatorLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val job = Job()

    companion object {
        const val WEEK_IN_MILLIS = 604800000L
    }

    init {
        loadNewsArticles()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    private fun loadNewsArticles() {
        launch(Dispatchers.IO) {
            dataProvider.requestLiveData {
                updateView(it)
            }
        }
    }

    fun onListRefreshed() {
        launch(Dispatchers.IO) {
            dataProvider.requestData {
                updateView(it)
            }
        }
    }

    private fun updateView(item: LiveData<NewsArticlesState>) {
        launch {
            withContext(Dispatchers.Main) {
                mediatorLiveData.addSource(item) { newsArticleState: NewsArticlesState? ->
                    if (newsArticleState != null) {
                        mediatorLiveData.value = when (newsArticleState) {
                            is NewsArticlesState.Loading -> NewsArticlesViewState.InProgress
                            is NewsArticlesState.Success -> NewsArticlesViewState.ShowNewsArticles(getGroupedArticles(newsArticleState.articles))
                            is NewsArticlesState.Error -> NewsArticlesViewState.ShowErrorMessage(newsArticleState.error)
                        }
                    }
                }
            }
        }
    }

    private fun updateView(item: NewsArticlesState) =
            launch {
                withContext(Dispatchers.Main) {
                    mediatorLiveData.value = when (item) {
                        is NewsArticlesState.Loading -> NewsArticlesViewState.InProgress
                        is NewsArticlesState.Success -> NewsArticlesViewState.ShowNewsArticles(getGroupedArticles(item.articles))
                        is NewsArticlesState.Error -> NewsArticlesViewState.ShowErrorMessage(item.error)
                    }
                }
            }

    private fun getGroupedArticles(articles: List<Article>): List<Any> {
        val favArticles = articles.filter { it.favourite }
        val articlesThisWeek = articles.filter {
            !it.favourite && it.published >= Date(System.currentTimeMillis() - WEEK_IN_MILLIS)
        }
        val articlesLastWeek = articles.filter {
            !it.favourite && it.published < Date(System.currentTimeMillis() - WEEK_IN_MILLIS)
        }
        val groupedList = mutableListOf<Any>()
        if (favArticles.isEmpty().not()) {
            groupedList.add("Favourites")
            groupedList.addAll(favArticles)
        }
        if (articlesThisWeek.isEmpty().not()) {
            groupedList.add("This week")
            groupedList.addAll(articlesThisWeek)
        }
        if (articlesLastWeek.isEmpty().not()) {
            groupedList.add("Last Week")
            groupedList.addAll(articlesLastWeek)
        }
        return groupedList
    }

}
