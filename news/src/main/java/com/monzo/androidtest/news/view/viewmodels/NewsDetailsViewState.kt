package com.monzo.androidtest.news.view.viewmodels

import com.monzo.androidtest.news.api.Article

sealed class NewsDetailsViewState {

    object InProgress: NewsDetailsViewState()

    class ShowNewsDetails(val newsArticle: Article) : NewsDetailsViewState()

    class ShowErrorMessage(val errorMessage: String) : NewsDetailsViewState()
}


