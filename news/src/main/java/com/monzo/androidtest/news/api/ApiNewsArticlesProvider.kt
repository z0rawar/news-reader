package com.monzo.androidtest.news.api

import android.util.Log
import com.monzo.androidtest.core.di.providers.DataMapper
import com.monzo.androidtest.core.di.providers.DataProvider
import com.monzo.androidtest.news.entities.NewsArticlesState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ApiNewsArticlesProvider(
        private val apiService: GuardianService,
        private val mapper: DataMapper<ApiArticleListResponse, List<Article>>
) : DataProvider<NewsArticlesState> {

    override fun requestData(callback: (item: NewsArticlesState) -> Unit) {
        apiService.searchArticles("fintech,brexit").enqueue(object : Callback<ApiArticleListResponse> {
            override fun onFailure(call: Call<ApiArticleListResponse>, t: Throwable) {
                Log.d("z", "failed " + t.localizedMessage)
                callback(NewsArticlesState.Error(t.localizedMessage))
            }

            override fun onResponse(call: Call<ApiArticleListResponse>, response: Response<ApiArticleListResponse>) {
                response.body()?.also { apiArticleListResponse ->
                    callback(NewsArticlesState.Success(mapper.encode(apiArticleListResponse)))
                }
            }
        })
    }
}