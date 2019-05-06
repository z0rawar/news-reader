package com.monzo.androidtest

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import com.monzo.androidtest.news.view.NewsListFragment

class HeadlinesActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, NewsListFragment())
            commit()
        }
    }
}
