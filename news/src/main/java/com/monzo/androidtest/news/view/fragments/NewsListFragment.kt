package com.monzo.androidtest.news.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.monzo.androidtest.news.view.adapters.ArticleAdapter
import com.monzo.androidtest.news.view.NewsConstants
import com.monzo.androidtest.news.view.viewmodels.NewsArticlesViewModel
import com.monzo.androidtest.news.view.viewmodels.NewsArticlesViewState
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_news_list.*
import javax.inject.Inject



class NewsListFragment : DaggerFragment(), ArticleAdapter.OnItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var newsArticlesViewModel: NewsArticlesViewModel
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsArticlesViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewsArticlesViewModel::class.java)
        articleAdapter = ArticleAdapter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.monzo.androidtest.news.R.layout.fragment_news_list, container, false)
        newsArticlesViewModel.newsArticlesViewState.observe(this,
                Observer { newState -> viewStateChanged(newState) })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvArticlesList.also {
            it.adapter = articleAdapter
            it.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
        srlArticlesList.setOnRefreshListener {
            newsArticlesViewModel.onListRefreshed()
        }
    }

    private fun viewStateChanged(newState: NewsArticlesViewState) {
        srlArticlesList.isRefreshing = newState is NewsArticlesViewState.InProgress
        when (newState) {
            is NewsArticlesViewState.InProgress -> Log.d("NewsListFragment", "Loading")
            is NewsArticlesViewState.ShowNewsArticles -> showArticles(newState.newsArticles)
            is NewsArticlesViewState.ShowErrorMessage -> showToast(newState.errorMessage)
        }
    }

    private fun showArticles(newsArticles: List<Any>) {
        val recyclerViewState = rvArticlesList.layoutManager?.onSaveInstanceState()
        articleAdapter.showArticles(newsArticles)
        rvArticlesList.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }

    private fun showToast(toastMsg: String) {
        Toast.makeText(activity, toastMsg, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(articleId: String) {
        //Refactor: Add proper navigation support
        val fragment = NewsDetailsFragment().apply {
            arguments = Bundle().apply {
                this.putString(NewsConstants.BUNDLE_PARAM_ARTICLE_ID, articleId)
            }
        }
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(com.monzo.androidtest.news.R.id.container, fragment)
            addToBackStack(NewsDetailsFragment::class.java.simpleName)
            commit()
        }
    }
}
