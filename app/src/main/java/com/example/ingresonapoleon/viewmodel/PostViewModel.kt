package com.example.ingresonapoleon.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ingresonapoleon.model.PostRepository
import com.example.ingresonapoleon.model.data.Post
import com.example.ingresonapoleon.model.data.PostBind
import com.example.ingresonapoleon.model.data.PostDB
import com.example.ingresonapoleon.services.ModelResponse
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class PostViewModel(private val postRepository: PostRepository) {

    // Variables Binding
    private val getPostInfoLiveData: MutableLiveData<UIState> = MutableLiveData()
    private val deleteAllPostLiveData: MutableLiveData<UIState> = MutableLiveData()
    private val deletePostLiveData: MutableLiveData<UIState> = MutableLiveData()

    // Variables LiveData
    fun getPostInfoLiveData(): LiveData<UIState> = getPostInfoLiveData
    fun deleteAllPostLiveData(): LiveData<UIState> = deleteAllPostLiveData
    fun deletePostLiveData(): LiveData<UIState> = deletePostLiveData

    @Suppress("UNCHECKED_CAST")
    fun getPosts() {
        getPostInfoLiveData.value = UIState.Loading
        postRepository.getPosts { response ->
            when(response) {
                is ModelResponse.OnSuccessApi -> {
                    postRepository.insertPostInDB(mapListForDB(response.result as ArrayList<Post>))
                    getPostInfoLiveData.postValue(UIState.Success(mapListForBind(response.result)))
                }
                is ModelResponse.OnSuccessDB -> {
                    getPostInfoLiveData.postValue(UIState.Success(mapListForDBBind(response.result as ArrayList<PostDB>)))
                }
                is ModelResponse.OnError -> {
                    val messageError = response.error
                    getPostInfoLiveData.postValue(UIState.Error(messageError))
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    fun deleteAllPostDB() {
        getPostInfoLiveData.value = UIState.Loading
        postRepository.deleteAllPostDB()
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onComplete = {
                    deleteAllPostLiveData.postValue(UIState.Success(true))
                },
                onError = {
                    deleteAllPostLiveData.postValue(UIState.Error("Error"))
                }
            )
    }

    @SuppressLint("CheckResult")
    fun deletePostDB(idPost: Int) {
        getPostInfoLiveData.value = UIState.Loading
        postRepository.deletePostDB(idPost)
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onComplete = {
                    deletePostLiveData.postValue(UIState.Success(true))
                },
                onError = {
                    deletePostLiveData.postValue(UIState.Error("Error"))
                }
            )
    }

    fun readPostDB(idPost: Int) {
        postRepository.readPostDB(idPost)
    }

    private fun mapListForBind(listPosts : MutableList<Post>): MutableList<PostBind> {
        val values: MutableList<PostBind>  = mutableListOf()
        listPosts.map { postApi ->
            val postBind = PostBind()
            if (postApi.id < 20) {
                postBind.idPost = postApi.id
                postBind.idUser = postApi.userId
                postBind.title = postApi.title
                postBind.body = postApi.body
                postBind.isRead = false
            } else {
                postBind.idPost = postApi.id
                postBind.idUser = postApi.userId
                postBind.title = postApi.title
                postBind.body = postApi.body
            }
            values.add(postBind)
        }
        return values
    }

    private fun mapListForDBBind(listPosts : MutableList<PostDB>): MutableList<PostBind> {
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

    private fun mapListForDB(listPosts : ArrayList<Post>): MutableList<PostDB> {
        val values: MutableList<PostDB>  = mutableListOf()
        mapListForBind(listPosts).map { postApi ->
            val postDB = PostDB(
                id = postApi.idPost,
                idUser = postApi.idUser,
                title = postApi.title,
                body = postApi.body,
                isRead = postApi.isRead,
                isFavorite = postApi.isFavorite)
            values.add(postDB)
        }
        return values
    }

}