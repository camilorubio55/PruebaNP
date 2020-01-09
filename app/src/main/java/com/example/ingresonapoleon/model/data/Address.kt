package com.example.ingresonapoleon.model.data

import androidx.annotation.Keep

@Keep
data class Address(
    val street: String = String(),
    val suite: String = String(),
    val city: String = String(),
    val zipcode: String = String(),
    val geo: Geo = Geo()
)