package com.example.ingresonapoleon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ingresonapoleon.model.UserRepository
import com.example.ingresonapoleon.model.data.User
import com.example.ingresonapoleon.model.data.UserBind
import com.example.ingresonapoleon.services.ModelResponse

class UserViewModel(private val userRepository: UserRepository) {

    // Variables Binding
    private val getUserInfoLiveData: MutableLiveData<UIState> = MutableLiveData()

    // Variables LiveData
    fun getUserInfoLiveData(): LiveData<UIState> = getUserInfoLiveData

    @Suppress("UNCHECKED_CAST")
    fun getUsers(idUser : Int) {
        getUserInfoLiveData.value = UIState.Loading
        userRepository.getUsers { response ->
            when(response) {
                is ModelResponse.OnSuccessApi -> {
                    //userRepository.insertUsersInDB(mapListForDB(response.result as ArrayList<User>))
                    val user = filterUserForId(idUser, response.result as ArrayList<User>)
                    getUserInfoLiveData.postValue(UIState.Success(mapUserForBind(user = user)))
                }
/*                is ModelResponse.OnSuccessDB -> {
                    getUserInfoLiveData.postValue(UIState.Success(mapListForBind(listUsersDB =  response.result as ArrayList<UserBD>)))
                }*/
                is ModelResponse.OnError -> {
                    val messageError = response.error
                    getUserInfoLiveData.postValue(UIState.Error(messageError))
                }
            }
        }
    }

    private fun filterUserForId(idUser: Int, listUsers: ArrayList<User>): User{
       return listUsers.find{ it.id == idUser }!!
    }

    private fun mapUserForBind(user : User): UserBind {
        return UserBind(
            name = user.name,
            email = user.email,
            phone = user.phone
        )
    }

}