package com.monzo.androidtest.news.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class NewsListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var newsArticlesViewModel: NewsArticlesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsArticlesViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewsArticlesViewModel::class.java)
    }
}
