package com.monzo.androidtest.news.providers

import com.monzo.androidtest.core.providers.DataMapper
import com.monzo.androidtest.core.providers.DataProvider
import com.monzo.androidtest.news.api.ApiArticleDetailResponse
import com.monzo.androidtest.news.api.ApiArticleList
import com.monzo.androidtest.news.api.ApiArticleListResponse
import com.monzo.androidtest.news.api.Article
import com.monzo.androidtest.news.api.GuardianService
import com.monzo.androidtest.news.entities.NewsArticlesState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiNewsArticlesProvider(
        private val apiService: GuardianService,
        private val mapper: DataMapper<ApiArticleListResponse, List<Article>>
) : DataProvider<NewsArticlesState> {

    companion object {
        const val SEARCH_QUERY_PARAM = "fintech,brexit"
        const val GET_ARTICLE_QUERY_PARAM = "body,thumbnail,headline"
    }

    override fun requestData(callback: (item: NewsArticlesState) -> Unit) {
        callback(NewsArticlesState.Loading)
        apiService.searchArticles(SEARCH_QUERY_PARAM).enqueue(object : Callback<ApiArticleListResponse> {
            override fun onFailure(call: Call<ApiArticleListResponse>, t: Throwable) {
                callback(NewsArticlesState.Error(t.localizedMessage))
            }

            override fun onResponse(call: Call<ApiArticleListResponse>, response: Response<ApiArticleListResponse>) {
                response.body()?.also { apiArticleListResponse ->
                    callback(NewsArticlesState.Success(mapper.encode(apiArticleListResponse)))
                }
            }
        })
    }

    override fun requestData(id: String, callback: (item: NewsArticlesState) -> Unit) {
        callback(NewsArticlesState.Loading)
        apiService.getArticle(id, GET_ARTICLE_QUERY_PARAM).enqueue(object : Callback<ApiArticleDetailResponse> {
            override fun onFailure(call: Call<ApiArticleDetailResponse>, t: Throwable) {
                callback(NewsArticlesState.Error(t.localizedMessage))
            }

            override fun onResponse(call: Call<ApiArticleDetailResponse>, response: Response<ApiArticleDetailResponse>) {
                response.body()?.also { apiArticleDetailResponse ->
                    val singleArticleList = ApiArticleListResponse(
                            ApiArticleList(listOf(apiArticleDetailResponse.response.content)))
                    callback(NewsArticlesState.Success(mapper.encode(singleArticleList)))
                }
            }

        })
    }

}