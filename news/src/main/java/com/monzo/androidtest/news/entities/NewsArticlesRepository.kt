package com.monzo.androidtest.news.entities

import com.monzo.androidtest.core.di.providers.DataPersister
import com.monzo.androidtest.core.di.providers.DataProvider
import com.monzo.androidtest.news.api.Article
import com.monzo.androidtest.news.di.NewsModule
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
                    }
                    callback(state)
                }
            } else {
                callback(NewsArticlesState.Success(articles))
            }
        }
    }

    override fun requestData(id: String, callback: (item: NewsArticlesState) -> Unit) {
        persister.requestData(id) { articles ->
            callback(NewsArticlesState.Success(articles))
            val article : Article? = articles[0]
//            if(article?.body.isNullOrEmpty()){ //TODO Change this to body
                provider.requestData(id){state->
                    if(state is NewsArticlesState.Success){
                        GlobalScope.launch (Dispatchers.IO){
                            persister.updateData(state.articles)
                        }
                    }
                    callback(state)
                }
//            }
        }
    }
}