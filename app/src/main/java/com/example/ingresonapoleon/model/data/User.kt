package com.example.ingresonapoleon.model.data

import androidx.annotation.Keep

@Keep
data class User(
    val id: Int = 0,
    val name: String = String(),
    val username: String = String(),
    val email: String = String(),
    val address: Address = Address(),
    val phone: String = String(),
    val website: String = String(),
    val company: Company = Company()
)