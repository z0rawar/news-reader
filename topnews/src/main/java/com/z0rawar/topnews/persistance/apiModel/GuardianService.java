package com.z0rawar.topnews.persistance.apiModel;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GuardianService {
    @GET("search?show-fields=headline,thumbnail&page-size=50")
    Call<ApiArticleListResponse> searchArticles(@Query("q") String searchTerm);

    @GET
    Call<ApiArticle> getArticle(@Url String articleUrl, @Query("show-fields") String fields);
}
