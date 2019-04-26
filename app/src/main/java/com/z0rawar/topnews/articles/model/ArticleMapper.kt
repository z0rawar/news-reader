package com.z0rawar.topnews.articles.model

import com.z0rawar.topnews.persistance.apiModel.ApiArticleListResponse
import java.util.*

class ArticleMapper {

    fun map(apiArticleListResponse: ApiArticleListResponse): List<Article> {
        val articles = ArrayList<Article>()

        for ((id, sectionId, sectionName, webPublicationDate, _, _, apiUrl, fields) in apiArticleListResponse.response.results) {

            var thumbnail: String
            if(fields == null) {
                thumbnail = ""
            } else {
                thumbnail = fields.thumbnail
            }

            var headline: String
            if(fields == null) {
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
}
