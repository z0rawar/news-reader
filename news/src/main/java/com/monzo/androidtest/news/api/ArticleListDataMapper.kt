package com.monzo.androidtest.news.api

import com.monzo.androidtest.core.di.providers.DataMapper

class ArticleListDataMapper : DataMapper<ApiArticleListResponse, List<Article>> {
    override fun encode(source: ApiArticleListResponse): List<Article> {
        val articles = ArrayList<Article>()

        for ((id, sectionId, sectionName, webPublicationDate, _, _, apiUrl, fields) in source.response.results) {

            var thumbnail: String
            if (fields == null) {
                thumbnail = ""
            } else {
                thumbnail = fields.thumbnail
            }

            var headline: String
            if (fields == null) {
                headline = ""
            } else {
                headline = fields.headline
            }

            articles.add(Article(id,
                    thumbnail,
                    sectionId,
                    sectionName,
                    webPublicationDate,
                    headline,
                    apiUrl))
        }

        return articles
    }

    override fun decode(source: List<Article>): ApiArticleListResponse {
        //TODO
        return ApiArticleListResponse(ApiArticleList(emptyList()))
    }

}