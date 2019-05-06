package com.monzo.androidtest.news.entities

import com.monzo.androidtest.news.api.Article

sealed class NewsDetailsState {

    object Loading : NewsDetailsState()

    class Success(val article: Article) : NewsDetailsState()

    class Error(val error : String) : NewsDetailsState()

}
