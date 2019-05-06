package com.monzo.androidtest.news.api

import com.monzo.androidtest.core.di.providers.DataMapper

class ArticleDataMapper : DataMapper<ApiArticle, List<Article>> {

    override fun encode(source: ApiArticle): List<Article> {
        var thumbnail: String
        if (source.fields?.thumbnail == null) {
            thumbnail = ""
        } else {
            thumbnail = source.fields.thumbnail
        }

        var headline: String
        if (source.fields?.headline == null) {
            headline = ""
        } else {
            headline = source.fields.headline
        }

        return listOf(Article(source.id,
                thumbnail,
                source.sectionId,
                source.sectionName,
                source.webPublicationDate,
                headline,
                source.apiUrl,
                source.fields?.body,
                false))
    }

}
