package com.monzo.androidtest.news.data

import com.monzo.androidtest.core.di.providers.DataMapper
import com.monzo.androidtest.core.di.providers.DataPersister
import com.monzo.androidtest.news.api.Article


class DatabaseNewsArticlesPersister(
        private val dao: ArticlesDao,
        private val mapper: DataMapper<Article, NewsArticle>)
    : DataPersister<List<Article>> {

    override fun persistData(data: List<Article>) {
        dao.insertAllArticles(data.map { mapper.encode(it) })
    }

    override fun requestData(callback: (item: List<Article>) -> Unit) {
        val articles = dao.getAllArticles().map { mapper.decode(it) }
        callback(articles)
    }
}

