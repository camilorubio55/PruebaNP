package com.example.ingresonapoleon.model

import com.example.ingresonapoleon.services.Connection
import com.example.ingresonapoleon.services.Endpoint
import com.example.ingresonapoleon.core.Constants
import com.example.ingresonapoleon.model.data.Post
import com.example.ingresonapoleon.model.data.PostDB
import com.example.ingresonapoleon.model.db.PostDao
import com.example.ingresonapoleon.services.ConnectionResponse
import com.example.ingresonapoleon.services.ModelResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Completable

class PostRepository(private val connection: Connection, private val postDao: PostDao) {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    enum class PostsAction : Endpoint {
        GET_POSTS {
            override val base: String get() = Constants.BASE_URL
            override val path: String get() = "/posts"
        }
    }

    fun getPosts(completion: (ModelResponse) -> Unit) {
        if (getPostDB().count() > 0) {
            completion(ModelResponse.OnSuccessDB(getPostDB()))
        } else {
            getPostApi { response ->
                when (response) {
                    is ModelResponse.OnSuccessApi -> {
                        completion(ModelResponse.OnSuccessApi(response.result))
                    }
                    is ModelResponse.OnError -> {
                        completion(ModelResponse.OnError(response.error))
                    }
                }
            }
        }
    }

    private fun getPostDB(): List<PostDB> {
        return postDao.getPostsDB()
    }

    fun getRefreshPost(completion: (ModelResponse) -> Unit) {
        getPostApi { response ->
            when (response) {
                is ModelResponse.OnSuccessApi -> {
                    completion(ModelResponse.OnSuccessApi(response.result))
                }
                is ModelResponse.OnError -> {
                    completion(ModelResponse.OnError(response.error))
                }
            }
        }
    }

    private fun getPostApi(completion: (ModelResponse) -> Unit) {
        connection.send(PostsAction.GET_POSTS) { response ->
            when (response) {
                is ConnectionResponse.OnFailure -> {
                    val error = Constants.ERROR_CONNECTION
                    completion(ModelResponse.OnError(error))
                }
                is ConnectionResponse.OnSuccess -> {
                    val type = Types.newParameterizedType(List::class.java, Post::class.java)
                    val adapter: JsonAdapter<List<Any>> = moshi.adapter(type)
                    val listPosts: List<Any> = adapter.fromJson(response.result)!!
                    completion(ModelResponse.OnSuccessApi(listPosts))
                }
            }
        }
    }

    fun insertPostInDB(listPosts: List<PostDB>) {
        postDao.insertAllDB(listPosts)
    }

    fun deleteAllPostDB(): Completable {
        return postDao.deleteAllPostDB()
    }

    fun deletePostDB(idPost: Int): Completable {
        return postDao.deletePostDB(idPost)
    }

    fun readPostDB(idPost: Int, isRead: Int): Completable {
        return postDao.readPostDB(idPost, isRead)
    }

    fun isFavoritePostDB(idPost: Int, isFavorite: Int): Completable {
        return postDao.isFavoritePostDB(idPost, isFavorite)
    }

}