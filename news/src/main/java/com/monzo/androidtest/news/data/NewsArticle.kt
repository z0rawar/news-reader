package com.monzo.androidtest.news.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsArticle(
        @PrimaryKey val id: String,
        val thumbnail: String,
        val sectionId: String,
        val sectionName: String,
        val published: Long,
        val title: String,
        val url: String
)
