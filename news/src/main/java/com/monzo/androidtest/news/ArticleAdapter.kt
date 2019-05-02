package com.monzo.androidtest.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.monzo.androidtest.news.api.Article
import kotlinx.android.synthetic.main.list_item_article.view.*
import java.util.*

internal class ArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val articles = ArrayList<Article>()

    override fun getItemCount(): Int = articles.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val articleViewHolder = holder as ArticleViewHolder
        articleViewHolder.bind(articles[position])
    }

    fun showArticles(articles: List<Article>) {
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    internal class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(article: Article) {
            itemView.tvArticleHeadline.text = article.title
            //Glide.with(context).load(article.getThumbnail()).into(thumbnailImageView);
        }
    }


}
