package com.example.ingresonapoleon.model

import com.example.ingresonapoleon.model.data.PostDB
import com.example.ingresonapoleon.model.db.PostDao
import com.example.ingresonapoleon.services.ModelResponse

class FavoritePostsRepository(private val postDao: PostDao) {

    fun getFavoritePostsDB(completion: (ModelResponse) -> Unit) {
        if (getPostDB().count() > 0) {
            completion(ModelResponse.OnSuccessDB(getPostDB()))
        } else {
            completion(ModelResponse.OnError("NO information found"))
        }
    }

    private fun getPostDB(): List<PostDB> {
        return postDao.getPostsFavDB()
    }
}