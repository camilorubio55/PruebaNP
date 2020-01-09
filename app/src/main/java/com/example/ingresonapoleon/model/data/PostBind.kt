package com.example.ingresonapoleon.model.data

import androidx.annotation.Keep

@Keep
data class PostBind(
    var id: Int = 0,
    var title: String = String(),
    var body: String = String()
)