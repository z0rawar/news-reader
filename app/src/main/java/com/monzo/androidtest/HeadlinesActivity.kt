package com.monzo.androidtest

import android.os.Bundle
import android.os.PersistableBundle
import com.z0rawar.topnews.view.NewsListFragment
import dagger.android.support.DaggerAppCompatActivity

class HeadlinesActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, NewsListFragment())
            commit()
        }
    }

}
