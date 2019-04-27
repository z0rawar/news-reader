package com.z0rawar.topnews.articles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.monzo.androidtest.R;
import com.z0rawar.topnews.articles.model.Article;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Article> articles = new ArrayList<>();
    private static Context context;

    public ArticleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ArticleViewHolder articleViewHolder = (ArticleViewHolder) holder;
        articleViewHolder.bind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    void showArticles(List<Article> articles) {
        this.articles.addAll(articles);
        notifyDataSetChanged();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.article_headline_textview)
        TextView headlineTextView;

        @BindView(R.id.article_thumbnail_imageview)
        ImageView thumbnailImageView;

        ArticleViewHolder(View view) {
            super(view);
        }

        void bind(Article article) {
            ButterKnife.bind(this, itemView);
            headlineTextView.setText(article.getTitle());
            Glide.with(context).load(article.getThumbnail()).into(thumbnailImageView);
        }
    }
}
