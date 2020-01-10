package com.example.ingresonapoleon.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ingresonapoleon.R
import com.example.ingresonapoleon.model.data.PostBind
import kotlinx.android.synthetic.main.post_item.view.*

class ListPostAdapter(private val clickPost: (Int, Int) -> Unit) : RecyclerView.Adapter<ListPostAdapter.ViewHolder>() {

    // Data
    private var dataItems: MutableList<PostBind> = mutableListOf()

    fun setData(data: MutableList<PostBind>) {
        this.dataItems.addAll(data)
        notifyDataSetChanged()
    }

    fun getData(): MutableList<PostBind> {
        return this.dataItems
    }

    fun updateRead(idPost: Int) {
        this.dataItems.find { it.idPost == idPost }?.isRead = true
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
        val v = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
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

            if (!post.isRead) {
                itemView.containerImage.visibility = View.VISIBLE
            } else {
                itemView.containerImage.visibility = View.INVISIBLE

            }

            itemView.setOnClickListener {
                clickPost(post.idPost,post.idUser)
            }
        }
    }
}