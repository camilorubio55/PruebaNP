package com.example.ingresonapoleon.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ingresonapoleon.R
import com.example.ingresonapoleon.model.data.PostBind
import kotlinx.android.synthetic.main.item_favorite_post.view.*

class ListFavoritePostsAdapter : RecyclerView.Adapter<ListFavoritePostsAdapter.ViewHolder>() {

    // Data
    private var dataItems: MutableList<PostBind> = mutableListOf()

    fun setData(data: MutableList<PostBind>) {
        this.dataItems.addAll(data)
        notifyDataSetChanged()
    }

    fun clearData() {
        this.dataItems.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataItems.count()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_post, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = dataItems[position]
        holder.bind(post)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: PostBind) {
            itemView.titleFavPost.text = post.title
            itemView.bodyFavPost.text = post.body
        }
    }
}