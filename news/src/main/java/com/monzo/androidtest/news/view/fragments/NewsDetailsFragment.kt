package com.monzo.androidtest.news.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.monzo.androidtest.news.R
import com.monzo.androidtest.news.api.Article
import com.monzo.androidtest.news.view.NewsConstants
import com.monzo.androidtest.news.view.viewmodels.FavouritesViewState
import com.monzo.androidtest.news.view.viewmodels.NewsDetailsViewModel
import com.monzo.androidtest.news.view.viewmodels.NewsDetailsViewState
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_news_details.*
import javax.inject.Inject

class NewsDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var newsDetailsViewModel: NewsDetailsViewModel
    private lateinit var articleId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsDetailsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewsDetailsViewModel::class.java)
        articleId = arguments?.getString(NewsConstants.BUNDLE_PARAM_ARTICLE_ID,
                NewsConstants.INVALID_ARTICLE_ID) ?: NewsConstants.INVALID_ARTICLE_ID
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news_details, container, false)
        newsDetailsViewModel.newsDetailsViewState.observe(this, Observer { detailViewStateChanged(it) })
        newsDetailsViewModel.favouritesViewState.observe(this, Observer { favouritesViewStateChanged(it) })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsDetailsViewModel.loadNewsArticleDetails(articleId)
        setupToolbar()
        ivFav.setOnClickListener { onFavClicked() }
    }

    private fun favouritesViewStateChanged(state: FavouritesViewState?) {
        when (state) {
            is FavouritesViewState.Favourite -> showFilledHeart()
            is FavouritesViewState.NonFavourite -> showEmptyHeart()
        }
    }

    private fun detailViewStateChanged(state: NewsDetailsViewState) {
        progressBar.visibility = if (state is NewsDetailsViewState.InProgress) View.VISIBLE else View.GONE
        when (state) {
            is NewsDetailsViewState.InProgress -> Log.d("NewsDetailsFragment", "Loading from API")
            is NewsDetailsViewState.ShowNewsDetails -> showNewsDetails(state.newsArticle)
            is NewsDetailsViewState.ShowErrorMessage -> showError(state.errorMessage)
        }
    }

    private fun showEmptyHeart() {
        ivFav.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_border_white_24dp))
    }

    private fun showFilledHeart() {
        ivFav.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_white_24dp))
    }

    private fun onFavClicked() {
        newsDetailsViewModel.onFavClicked(articleId)
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(tbDetail)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        ctlDetail.setExpandedTitleColor(resources.getColor(android.R.color.transparent))
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun showNewsDetails(newsArticle: Article) {
        tvTitle.text = newsArticle.title
        Glide.with(this).load(newsArticle.thumbnail).into(ivThumbnail)
        if (newsArticle.favourite) ivFav.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_white_24dp))
        newsArticle.body?.let {
            wvDetail.settings.setJavaScriptEnabled(true)
            wvDetail.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            //Trick to set max width of WebView to match the device width
            wvDetail.loadDataWithBaseURL(null,
                    "${getString(R.string.webview_width_fix)}${newsArticle.body}",
                    "text/html", "utf-8", null)

        }

    }
}
