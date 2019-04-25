package com.monzo.androidtest.articles;


import com.monzo.androidtest.api.GuardianService;
import com.monzo.androidtest.api.model.ApiArticle;
import com.monzo.androidtest.articles.model.Article;
import com.monzo.androidtest.articles.model.ArticleMapper;

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
        return guardianService.searchArticles("fintech,brexit").map(articleMapper::map);
    }

    Single<ApiArticle> getArticle(String articleUrl) {
        return guardianService.getArticle(articleUrl, "main,body,headline,thumbnail");
    }
}
