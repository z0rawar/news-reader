package com.monzo.androidtest.news.data

import com.monzo.androidtest.core.di.providers.DataMapper
import com.monzo.androidtest.news.api.Article
import java.util.*

class DatabaseArticlesMapper : DataMapper<Article, NewsArticle> {
    override fun encode(source: Article): NewsArticle {

        return NewsArticle(id = source.id,
                thumbnail = source.thumbnail,
                published = source.published.time,
                sectionId = source.sectionId,
                sectionName = source.sectionName,
                title = source.title,
                url = source.url,
                body = source.body,
                favourite = source.favourite)
    }

    override fun decode(source: NewsArticle): Article {
        return Article(id = source.id,
                thumbnail = source.thumbnail,
                published = Date(source.published), //TODO Move to utils, extension fucntion
                sectionId = source.sectionId,
                sectionName = source.sectionName,
                title = source.title,
                url = source.url,
                body = source.body,
                favourite = source.favourite)
    }
}