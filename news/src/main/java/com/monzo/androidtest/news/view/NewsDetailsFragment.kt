package com.monzo.androidtest.news.view

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.monzo.androidtest.news.R
import com.monzo.androidtest.news.api.Article
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
        articleId = arguments?.getString("articleId", "invalid") ?: "invalid"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news_details, container, false)
        newsDetailsViewModel.newsDetailsViewState.observe(this, Observer {
            when (it) {
                is NewsDetailsViewState.InProgress -> showLoading()
                is NewsDetailsViewState.ShowNewsDetails -> showNewsDetails(it.newsArticle)
                is NewsDetailsViewState.ShowErrorMessage -> showError(it.errorMessage)
            }
        })
        newsDetailsViewModel.favouritesViewState.observe(this, Observer {
            when(it){
                is FavouritesViewState.Favourite -> showFilledHeart()
                is FavouritesViewState.NonFavourite -> showEmptyHeart()
            }
        })
        return view
    }

    private fun showEmptyHeart() {
        ivFav.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_border_white_24dp))
    }

    private fun showFilledHeart() {
        ivFav.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_white_24dp))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsDetailsViewModel.loadNewsArticleDetails(articleId)
        setupToolbar()
        ivFav.setOnClickListener { onFavClicked()}
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

    private fun showNewsDetails(newsArticle: Article) {
        tvTitle.text = newsArticle.title
        Glide.with(this).load(newsArticle.thumbnail).into(ivThumbnail)
        newsArticle.body?.let { tvBody.text = Html.fromHtml(it) }
        if (newsArticle.favourite) ivFav.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_white_24dp))
    }

    private fun showLoading() {
        Toast.makeText(activity, "Loadiong..", Toast.LENGTH_SHORT).show()
    }


}
