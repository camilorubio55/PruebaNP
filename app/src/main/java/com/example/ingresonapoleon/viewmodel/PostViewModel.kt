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

    private fun mapListForBind(listPosts : ArrayList<Post>): MutableList<PostBind> {
        val values: MutableList<PostBind>  = mutableListOf()
        listPosts.map { postApi ->
            val postBind = PostBind()
            postBind.idPost = postApi.id
            postBind.idUser = postApi.userId
            postBind.title = postApi.title
            postBind.body = postApi.body
            values.add(postBind)
        }
        return values
    }

}