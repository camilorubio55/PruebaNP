package com.example.ingresonapoleon.model.data

import androidx.annotation.Keep

@Keep
data class PostBind(
    var idPost: Int = 0,
    var idUser: Int = 0,
    var title: String = String(),
    var body: String = String(),
    var isRead: Boolean = true,
    var isFavorite: Boolean = false
)