package com.monzo.androidtest.news.view.viewmodels

sealed class FavouritesViewState {

    object Favourite : FavouritesViewState()
    object NonFavourite : FavouritesViewState()


}
