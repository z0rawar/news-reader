package com.z0rawar.topnews.articles;

import com.z0rawar.topnews.articles.model.Article;
import com.monzo.androidtest.common.BasePresenter;
import com.monzo.androidtest.common.BasePresenterView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

class ArticlesPresenter extends BasePresenter<ArticlesPresenter.View> {
    private final Scheduler uiScheduler;
    private final Scheduler ioScheduler;
    private final ArticlesRepository articlesRepository;

    ArticlesPresenter(Scheduler uiScheduler, Scheduler ioScheduler, ArticlesRepository articlesRepository) {
        this.uiScheduler = uiScheduler;
        this.ioScheduler = ioScheduler;
        this.articlesRepository = articlesRepository;
    }

    @Override
    public void register(View view) {
        super.register(view);

        // TODO: error handling
        addToUnsubscribe(view.onRefreshAction()
                .doOnNext(ignored -> view.showRefreshing(true))
                .switchMapSingle(ignored -> articlesRepository.latestFintechArticles().subscribeOn(ioScheduler))
                .observeOn(uiScheduler)
                .subscribe(articles -> {
                    view.showRefreshing(false);
                    view.showArticles(articles);
                }));

        addToUnsubscribe(view.onArticleClicked()
                .subscribe(ignored -> view.openArticleDetail()));
    }

    interface View extends BasePresenterView {
        void showRefreshing(boolean isRefreshing);
        void showArticles(List<Article> articles);

        Observable<Article> onArticleClicked();
        Observable<Object> onRefreshAction();

        void openArticleDetail();
    }
}