package com.example.ingresonapoleon.core

import com.example.ingresonapoleon.model.data.PostBind
import com.example.ingresonapoleon.model.data.PostDB

interface MapListDBForBind {

    fun mapListDBForBind(listPosts : MutableList<PostDB>): MutableList<PostBind> {
        val values: MutableList<PostBind>  = mutableListOf()
        listPosts.map { postDB ->
            val postBind = PostBind()
            postBind.idPost = postDB.id
            postBind.idUser = postDB.idUser
            postBind.title = postDB.title
            postBind.body = postDB.body
            postBind.isRead = postDB.isRead
            postBind.isFavorite = postDB.isFavorite
            values.add(postBind)
        }
        return values
    }
}