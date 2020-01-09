package com.example.ingresonapoleon.model.data

import androidx.annotation.Keep

@Keep
data class Post(
    val userId: Int = 0,
    val id: Int = 0,
    val title: String = String(),
    val body: String = String()
)