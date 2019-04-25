package com.monzo.androidtest.api;

import com.monzo.androidtest.api.model.ApiArticle;
import com.monzo.androidtest.api.model.ApiArticleListResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GuardianService {
    @GET("search?show-fields=headline,thumbnail&page-size=50")
    Single<ApiArticleListResponse> searchArticles(@Query("q") String searchTerm);

    @GET
    Single<ApiArticle> getArticle(@Url String articleUrl, @Query("show-fields") String fields);
}
