package com.monzo.androidtest.news.view

sealed class FavouritesViewState {

    class Favourite : FavouritesViewState()
    class NonFavourite : FavouritesViewState()


}
