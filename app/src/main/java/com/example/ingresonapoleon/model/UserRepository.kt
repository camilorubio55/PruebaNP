package com.example.ingresonapoleon.model

import com.example.ingresonapoleon.core.Constants
import com.example.ingresonapoleon.model.data.User
import com.example.ingresonapoleon.services.Connection
import com.example.ingresonapoleon.services.ConnectionResponse
import com.example.ingresonapoleon.services.Endpoint
import com.example.ingresonapoleon.services.ModelResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class UserRepository(private val connection : Connection) {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    enum class UsersAction : Endpoint {
        GET_USERS {
            override val base: String get() = Constants.BASE_URL
            override val path: String get() = "/users"
        }
    }

    fun getUser(completion: (ModelResponse) -> Unit) {
        connection.send(UsersAction.GET_USERS) { response ->
            when(response) {
                is ConnectionResponse.OnFailure -> {
                    val error =  Constants.ERROR_CONNECTION
                    completion(ModelResponse.OnError(error))
                }
                is ConnectionResponse.OnSuccess -> {
                    val type = Types.newParameterizedType(List::class.java, User::class.java)
                    val adapter: JsonAdapter<List<Any>> = moshi.adapter(type)
                    val listUsers: List<Any> = adapter.fromJson(response.result)!!
                    completion(ModelResponse.OnSuccessApi(listUsers))
                }
            }
        }
    }
}