package com.monzo.androidtest.news.entities

import com.monzo.androidtest.core.di.providers.DataPersister
import com.monzo.androidtest.core.di.providers.DataProvider
import com.monzo.androidtest.news.api.Article
import com.monzo.androidtest.news.di.NewsModule
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
                        //TODO GlobalScope
                        persister.persistData(state.articles)
                    }
                    callback(state)
                }
            } else {
                callback(NewsArticlesState.Success(articles))
            }
        }
    }
}