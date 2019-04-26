package com.z0rawar.topnews.persistance.apiModel

import java.util.*

data class ApiArticle(
        val id: String,
        val sectionId: String,
        val sectionName: String,
        val webPublicationDate: Date,
        val webTitle: String,
        val webUrl: String,
        val apiUrl: String,
        val fields: ApiArticleFields?
) 

