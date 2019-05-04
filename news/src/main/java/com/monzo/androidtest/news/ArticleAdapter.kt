package com.monzo.androidtest.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.monzo.androidtest.news.api.Article
import kotlinx.android.synthetic.main.list_item_article.view.*
import kotlinx.android.synthetic.main.list_item_header.view.*
import java.util.*

internal class ArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val articles = ArrayList<Any>()

    override fun getItemCount(): Int = articles.size

    override fun getItemViewType(position: Int): Int {
        return if (articles[position] is String) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == 0) {
            val view = layoutInflater.inflate(R.layout.list_item_header, parent, false)
            HeaderViewHolder(view)
        }
        else {
            val view = layoutInflater.inflate(R.layout.list_item_article, parent, false)
            ArticleViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType ==0) {
            val headerViewHolder = holder as HeaderViewHolder
            headerViewHolder.bind(articles[position] as String)
        } else{
            val articleViewHolder = holder as ArticleViewHolder
            articleViewHolder.bind(articles[position] as Article)
        }
    }

    fun showArticles(articles: List<Any>) {
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    internal class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(article: Article) {
            itemView.tvArticleHeadline.text = article.title
            //Glide.with(context).load(article.getThumbnail()).into(thumbnailImageView);
        }
    }

    internal class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(header: String) {
            itemView.tvHeader.text = header
        }
    }


}
