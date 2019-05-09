package com.monzo.androidtest.news.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.monzo.androidtest.news.R
import com.monzo.androidtest.news.api.Article
import kotlinx.android.synthetic.main.list_item_article.view.*
import kotlinx.android.synthetic.main.list_item_header.view.*
import java.text.SimpleDateFormat
import java.util.*


internal class ArticleAdapter(val listener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val articles = ArrayList<Any>()

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

    override fun getItemCount(): Int = articles.size

    override fun getItemViewType(position: Int): Int {
        //TODO Refactor: Implement a ListItem interface that overrides getViewType and returns VIEW_TYPE Enum
        return if (articles[position] is String) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == 0) {
            val view = layoutInflater.inflate(R.layout.list_item_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = layoutInflater.inflate(R.layout.list_item_article, parent, false)
            ArticleViewHolder(view, listener, dateFormat)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 0) {
            val headerViewHolder = holder as HeaderViewHolder
            headerViewHolder.bind(articles[position] as String)
        } else {
            val articleViewHolder = holder as ArticleViewHolder
            articleViewHolder.bind(articles[position] as Article)
        }
    }

    fun showArticles(articles: List<Any>) {
        val difference = DiffUtil.calculateDiff(ArticlesDiffUtil(this.articles, articles))
        this.articles.clear()
        this.articles.addAll(articles)
        difference.dispatchUpdatesTo(this)
    }

    internal class ArticleViewHolder(
            view: View,
            private val listener: OnItemClickListener,
            private val dateFormat: SimpleDateFormat
    ) : RecyclerView.ViewHolder(view) {

        fun bind(article: Article) {
            itemView.tvArticleHeadline.text = article.title
            itemView.setOnClickListener { listener.onItemClick(article.id) }
            itemView.tvArticleDate.text = dateFormat.format(article.published)
            Glide.with(itemView.ivArticleThumbnail).load(article.thumbnail)
                    .apply(RequestOptions.circleCropTransform())
                    .into(itemView.ivArticleThumbnail)
        }
    }

    internal class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(header: String) {
            itemView.tvHeader.text = header
        }
    }

    interface OnItemClickListener {
        fun onItemClick(articleId: String)
    }

    private class ArticlesDiffUtil(
            private val oldList: List<Any>,
            private val newList: List<Any>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList[oldItemPosition] == newList[newItemPosition]

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return if (oldList[oldItemPosition] is String)
                true
            else (oldList[oldItemPosition] as Article).id == (newList[newItemPosition] as Article).id
        }

    }


}
