package com.monzo.androidtest.news.mapper

import com.monzo.androidtest.core.providers.DataMapper
import com.monzo.androidtest.news.api.ApiArticle
import com.monzo.androidtest.news.api.ApiArticleListResponse
import com.monzo.androidtest.news.api.Article

class ArticleListDataMapper(private val articleDataMapper: DataMapper<ApiArticle, List<Article>>) :
        DataMapper<ApiArticleListResponse, List<Article>> {


    override fun encode(source: ApiArticleListResponse): List<Article> {
        val articles = ArrayList<Article>()
        for (article in source.response.results) {
            articles.addAll(articleDataMapper.encode(article))
        }
        return articles
    }
}