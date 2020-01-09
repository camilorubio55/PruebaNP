package com.example.ingresonapoleon.model.data

import androidx.annotation.Keep

@Keep
data class Company(
    val name: String = String(),
    val catchPhrase: String = String(),
    val bs: String = String()
)