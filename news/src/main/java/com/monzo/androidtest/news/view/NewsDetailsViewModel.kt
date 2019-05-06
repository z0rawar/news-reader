package com.monzo.androidtest.news.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.monzo.androidtest.core.di.providers.DataProvider
import com.monzo.androidtest.news.di.NewsModule
import com.monzo.androidtest.news.entities.NewsArticlesState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class NewsDetailsViewModel @Inject constructor(
        @param:Named(NewsModule.LOCAL_DATASOURCE) private val dataProvider: DataProvider<NewsArticlesState>
) : ViewModel(), CoroutineScope {

    private val job = Job()
    private val mutableLiveData: MutableLiveData<NewsDetailsViewState> = MutableLiveData()

    val newsDetailsViewState: LiveData<NewsDetailsViewState>
        get() = mutableLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun loadNewsArticleDetails(id: String) {
        launch(Dispatchers.IO) {
            dataProvider.requestData(id) { item ->
                updateView(item)
            }
        }
    }

    private fun updateView(item: NewsArticlesState) {
        launch(Dispatchers.Main) {
            mutableLiveData.value = when (item) {
                is NewsArticlesState.Loading -> NewsDetailsViewState.InProgress
                is NewsArticlesState.Success -> NewsDetailsViewState.ShowNewsDetails(item.articles[0])
                is NewsArticlesState.Error -> NewsDetailsViewState.ShowErrorMessage(item.error)
            }
        }
    }
}
