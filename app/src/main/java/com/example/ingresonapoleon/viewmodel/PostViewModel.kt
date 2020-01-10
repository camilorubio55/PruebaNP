package com.example.ingresonapoleon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ingresonapoleon.model.PostRepository
import com.example.ingresonapoleon.model.data.Post
import com.example.ingresonapoleon.model.data.PostBind
import com.example.ingresonapoleon.services.ModelResponse

class PostViewModel(private val postRepository: PostRepository) {

    // Variables Binding
    private val getPostInfoLiveData: MutableLiveData<UIState> = MutableLiveData()

    // Variables LiveData
    fun getPostInfoLiveData(): LiveData<UIState> = getPostInfoLiveData

    @Suppress("UNCHECKED_CAST")
    fun getPosts() {
        //getPostInfoLiveData.value = UIState.Loading
        postRepository.getPost { response ->
            when(response) {
                is ModelResponse.OnSuccessApi -> {
                    getPostInfoLiveData.postValue(UIState.Success(mapListForBind(response.result as ArrayList<Post>)))
                }
                is ModelResponse.OnError -> {
                    val messageError = response.error
                    getPostInfoLiveData.postValue(UIState.Error(messageError))
                }
            }
        }
    }

/*    @Suppress("CAST_NEVER_SUCCEEDS")
    private fun postsWithIndicator(listPosts : MutableList<Post>): MutableList<PostBind> {
        val values: MutableList<PostBind>  = mutableListOf()
        mapListForBind(listPosts).map {
            if (it.idPost < 20) {
                values.add(PostBind(
                    idPost = it.idPost,
                    idUser = it.idUser,
                    title = it.title,
                    body = it.body,
                    isRead = false
                ))
            } else {
                values.add(it)
            }
        }
        return values
    }*/

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

}