package com.monzo.androidtest.news;

import androidx.appcompat.app.AppCompatActivity;

public class ArticlesActivity extends AppCompatActivity {
//        implements ArticlesPresenter.View {
////    @BindView(R.id.toolbar)
//    Toolbar toolbar;
//
////    @BindView(R.id.articles_swiperefreshlayout)
//    SwipeRefreshLayout swipeRefreshLayout;
//
////    @BindView(R.id.articles_recyclerview)
//    RecyclerView recyclerView;
//
//    private ArticlesPresenter presenter;
//    private ArticleAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_article_list);
//
////        ButterKnife.bind(this);
//        setSupportActionBar(toolbar);
//
//        adapter = new ArticleAdapter(this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//
////        presenter = HeadlinesApp.from(getApplicationContext()).inject(this);
//        presenter.register(this);
//    }
//
//    @Override
//    protected void onDestroy() {
//        presenter.unregister();
//        super.onDestroy();
//    }
//
//    @Override
//    public void showArticles(List<Article> articles) {
//        adapter.showArticles(articles);
//    }
//
//    @Override
//    public Observable<Article> onArticleClicked() {
//        // TODO: return the article clicked on
//        return Observable.empty();
//    }
//
//    @Override
//    public Observable<Object> onRefreshAction() {
//        return Observable.create(emitter -> {
//            swipeRefreshLayout.setOnRefreshListener(() -> emitter.onNext(null));
//            emitter.setCancellable(() -> swipeRefreshLayout.setOnRefreshListener(null));
//        }).startWith(Event.IGNORE);
//    }
//
//    @Override
//    public void openArticleDetail() {
////        Toast.makeText(this, "Open the actual article!", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void showRefreshing(boolean isRefreshing) {
//        swipeRefreshLayout.setRefreshing(isRefreshing);
//    }
}
