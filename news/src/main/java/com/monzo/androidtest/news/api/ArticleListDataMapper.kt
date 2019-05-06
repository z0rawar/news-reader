package com.monzo.androidtest.news.api

import com.monzo.androidtest.core.di.providers.DataMapper

class ArticleListDataMapper(val articleDataMapper: DataMapper<ApiArticle, List<Article>>) :
        DataMapper<ApiArticleListResponse, List<Article>> {


    override fun encode(source: ApiArticleListResponse): List<Article> {
        val articles = ArrayList<Article>()

//        for ((id, sectionId, sectionName, webPublicationDate, _, _, apiUrl, fields) in source.response.results) {

        for(article in source.response.results) {
            articles.addAll(articleDataMapper.encode(article))
        }
//        }

        return articles
    }
}