package com.monzo.androidtest.news.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.monzo.androidtest.core.providers.DataMapper
import com.monzo.androidtest.core.providers.DataPersister
import com.monzo.androidtest.news.api.Article
import com.monzo.androidtest.news.data.ArticlesDao
import com.monzo.androidtest.news.data.NewsArticle

class DatabaseNewsArticlesPersister(
        private val dao: ArticlesDao,
        private val mapper: DataMapper<Article, NewsArticle>)
    : DataPersister<List<Article>> {

    override fun insertData(data: List<Article>) {
        dao.insertAllArticles(data.map { mapper.encode(it) })
    }

    override fun requestData(callback: (item: List<Article>) -> Unit) {
        val articles = dao.getAllArticles().map { mapper.decode(it) }
        callback(articles)
    }

    override fun requestLiveData(callback: (item: LiveData<List<Article>>) -> Unit) {
        val articlesLiveData = Transformations.map(dao.getAllArticlesLiveData())
        { input ->
            input.map {
                mapper.decode(it)
            }
        }
        callback(articlesLiveData)
    }

    override fun requestData(id: String, callback: (item: List<Article>) -> Unit) {
        val newsArticle = dao.getArticleById(id)
        val article = mapper.decode(newsArticle)
        callback(listOf(article))
    }

    override fun updateData(data: List<Article>) {
        val article = data[0]
        dao.updateArticle(mapper.encode(article))
    }
}



