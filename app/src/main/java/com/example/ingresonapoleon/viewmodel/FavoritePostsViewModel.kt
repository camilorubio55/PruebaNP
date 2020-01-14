package com.example.ingresonapoleon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ingresonapoleon.core.MapListDBForBind
import com.example.ingresonapoleon.model.FavoritePostsRepository
import com.example.ingresonapoleon.model.data.PostDB
import com.example.ingresonapoleon.services.ModelResponse

class FavoritePostsViewModel(private val favoritePostsRepository: FavoritePostsRepository): MapListDBForBind {

    // Variables Binding
    private val getFavoritePostsInfoLiveData: MutableLiveData<UIState> = MutableLiveData()

    // Variables LiveData
    fun getFavoritePostsInfoLiveData(): LiveData<UIState> = getFavoritePostsInfoLiveData

    @Suppress("UNCHECKED_CAST")
    fun getFavoritePosts() {
        getFavoritePostsInfoLiveData.value = UIState.Loading
        favoritePostsRepository.getFavoritePostsDB { response ->
            when(response) {
                is ModelResponse.OnSuccessDB -> {
                    getFavoritePostsInfoLiveData.postValue(UIState.Success(mapListDBForBind(response.result as ArrayList<PostDB>)))
                }
                is ModelResponse.OnError -> {
                    val messageError = response.error
                    getFavoritePostsInfoLiveData.postValue(UIState.Error(messageError))
                }
            }
        }
    }
}