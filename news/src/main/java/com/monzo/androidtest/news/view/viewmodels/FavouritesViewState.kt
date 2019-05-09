package com.monzo.androidtest.news.view.viewmodels

sealed class FavouritesViewState {

    class Favourite : FavouritesViewState()
    class NonFavourite : FavouritesViewState()


}
