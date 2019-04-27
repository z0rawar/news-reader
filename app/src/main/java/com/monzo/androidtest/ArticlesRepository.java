package com.z0rawar.topnews.articles;


import com.z0rawar.topnews.persistance.apiModel.GuardianService;
import com.z0rawar.topnews.persistance.apiModel.ApiArticle;
import com.z0rawar.topnews.articles.model.Article;
import com.z0rawar.topnews.articles.model.ArticleMapper;

import java.util.List;

import io.reactivex.Single;


class ArticlesRepository {
    private final GuardianService guardianService;
    private final ArticleMapper articleMapper;

    public ArticlesRepository(GuardianService guardianService, ArticleMapper articleMapper) {
        this.guardianService = guardianService;
        this.articleMapper = articleMapper;
    }

    Single<List<Article>> latestFintechArticles() {
//        return guardianService.searchArticles("fintech,brexit").map(articleMapper::map);
        return Single.just(null);
    }

    Single<ApiArticle> getArticle(String articleUrl) {
//        return guardianService.getArticle(articleUrl, "main,body,headline,thumbnail");
        return Single.just(null);
    }
}
