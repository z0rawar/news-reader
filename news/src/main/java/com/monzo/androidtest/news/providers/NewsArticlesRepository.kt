package com.monzo.androidtest.news.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.monzo.androidtest.core.providers.DataPersister
import com.monzo.androidtest.core.providers.DataProvider
import com.monzo.androidtest.news.api.Article
import com.monzo.androidtest.news.di.NewsModule
import com.monzo.androidtest.news.entities.NewsArticlesState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Named

class NewsArticlesRepository(
        private val persister: DataPersister<List<Article>>,
        @param:Named(NewsModule.REMOTE_DATASOURCE) private val provider: DataProvider<NewsArticlesState>
) : DataProvider<NewsArticlesState> {

    override fun requestData(callback: (item: NewsArticlesState) -> Unit) {
        persister.requestData { articles ->
            if (articles.isEmpty()) {
                provider.requestData { state ->
                    if (state is NewsArticlesState.Success) {
                        GlobalScope.launch(Dispatchers.IO) {
                            persister.insertData(state.articles)
                        }
                    } else
                        callback(state)
                }
            } else {
                callback(NewsArticlesState.Success(articles))
            }
        }
    }

    override fun requestLiveData(callback: (item: LiveData<NewsArticlesState>) -> Unit) {
        val mediatorLiveData = MediatorLiveData<NewsArticlesState>()
        persister.requestLiveData { liveData ->
            mediatorLiveData.addSource(liveData) { articles: List<Article>? ->
                when {
                    articles == null -> mediatorLiveData.value = NewsArticlesState.Error("Error loading articles")
                    articles.isEmpty() -> {
                        mediatorLiveData.value = NewsArticlesState.Loading
                        provider.requestData { state ->
                            if (state is NewsArticlesState.Success) {
                                GlobalScope.launch(Dispatchers.IO) {
                                    persister.insertData(state.articles)
                                }
                            } else
                                mediatorLiveData.value = state
                        }
                    }
                    else -> mediatorLiveData.value = NewsArticlesState.Success(articles)
                }
            }
            callback(mediatorLiveData)
        }
    }

    override fun requestData(id: String, callback: (item: NewsArticlesState) -> Unit) {
        persister.requestData(id) { articles ->
            callback(NewsArticlesState.Success(articles))
            val article: Article = articles[0]
            if (article.body.isNullOrEmpty()) {
                provider.requestData(id) { state ->
                    if (state is NewsArticlesState.Success) {
                        GlobalScope.launch(Dispatchers.IO) {
                            persister.updateData(state.articles)
                        }
                    }
                    when (state) {
                        is NewsArticlesState.Success -> {
                            val fullArticle = article.copy(body = state.articles[0].body)
                            callback(NewsArticlesState.Success(listOf(fullArticle)))
                        }
                        else -> callback(state)
                    }

                }
            }
        }
    }
}