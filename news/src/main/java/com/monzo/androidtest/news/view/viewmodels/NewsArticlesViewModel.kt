package com.monzo.androidtest.news.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.monzo.androidtest.core.providers.DataProvider
import com.monzo.androidtest.news.api.Article
import com.monzo.androidtest.news.di.NewsModule
import com.monzo.androidtest.news.entities.NewsArticlesState
import com.monzo.androidtest.news.view.NewsConstants
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
        @param:Named(NewsModule.LOCAL_DATASOURCE) private val dataProvider: DataProvider<NewsArticlesState>,
        @param:Named(NewsModule.UI_CONTEXT) private val uiContext: CoroutineContext = Dispatchers.Main,
        @param:Named(NewsModule.IO_CONTEXT) private val ioContext: CoroutineContext = Dispatchers.IO
) : ViewModel(), CoroutineScope {

    private var mediatorLiveData: MediatorLiveData<NewsArticlesViewState> = MediatorLiveData()
    val newsArticlesViewState: LiveData<NewsArticlesViewState>
        get() = mediatorLiveData

    override val coroutineContext: CoroutineContext
        get() = uiContext + job

    private val job = Job()

    companion object {
        const val WEEK_IN_MILLIS = 100 * 60 * 60 * 24 * 7L
    }

    init {
        loadNewsArticles()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    private fun loadNewsArticles() {
        launch(ioContext) {
            dataProvider.requestLiveData {
                updateView(it)
            }
        }
    }

    fun onListRefreshed() {
        launch(ioContext) {
            dataProvider.requestData {
                updateView(it)
            }
        }
    }

    private fun updateView(item: LiveData<NewsArticlesState>) {
        launch {
            withContext(uiContext) {
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
                withContext(uiContext) {
                    mediatorLiveData.value = when (item) {
                        is NewsArticlesState.Loading -> NewsArticlesViewState.InProgress
                        is NewsArticlesState.Success -> NewsArticlesViewState.ShowNewsArticles(getGroupedArticles(item.articles))
                        is NewsArticlesState.Error -> NewsArticlesViewState.ShowErrorMessage(item.error)
                    }
                }
            }

    /**
     * A crude method to filter a list of articles and group them week wise
     *
     * TODO Refactor: This can be vastly improved by using a Map<Date,List<Article>> structure
     * Also, the WEEK_IN_MILLIS is a quick hack which does not give the exact day
     * This should be replaced by a simpler Calendar.add(Calendar.DAY_OF_YEAR, -days) implementation
     */
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
            groupedList.add(NewsConstants.FAVOURITES_HEADER)
            groupedList.addAll(favArticles)
        }
        if (articlesThisWeek.isEmpty().not()) {
            groupedList.add(NewsConstants.THIS_WEEK_HEADER)
            groupedList.addAll(articlesThisWeek)
        }
        if (articlesLastWeek.isEmpty().not()) {
            groupedList.add(NewsConstants.LAST_WEEK_HEADER)
            groupedList.addAll(articlesLastWeek)
        }
        return groupedList
    }

}
