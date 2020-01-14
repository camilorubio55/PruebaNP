package com.example.ingresonapoleon.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ingresonapoleon.R
import com.example.ingresonapoleon.model.data.PostBind
import kotlinx.android.synthetic.main.item_post.view.*

class ListPostAdapter(private val clickPost: (Int, Int, Boolean) -> Unit,
                      private val changePostFav:(Int, Boolean) -> Unit,
                      private val deletePost: (Int) -> Unit) : RecyclerView.Adapter<ListPostAdapter.ViewHolder>() {

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
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = dataItems[position]
        holder.bind(post)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: PostBind) {
            itemView.titlePost.text = post.title
            itemView.bodyPost.text = post.body
            itemView.postFav.isChecked = post.isFavorite
            if (!post.isRead) {
                itemView.imageRead.visibility = View.VISIBLE
            } else {
                itemView.imageRead.visibility = View.INVISIBLE

            }

            itemView.postFav.setOnCheckedChangeListener { _, isChecked ->
                changePostFav(post.idPost, isChecked)
            }

            itemView.setOnLongClickListener {
                deletePost(post.idPost)
                true
            }

            itemView.setOnClickListener {
                clickPost(post.idPost, post.idUser, true)
            }
        }
    }
}