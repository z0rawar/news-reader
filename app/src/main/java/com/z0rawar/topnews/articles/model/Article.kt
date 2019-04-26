package com.z0rawar.topnews.articles.model

import java.util.*

data class Article(
        val id: String,
        val thumbnail: String,
        val sectionId: String,
        val sectionName: String,
        val published: Date,
        val title: String,
        val url: String
)