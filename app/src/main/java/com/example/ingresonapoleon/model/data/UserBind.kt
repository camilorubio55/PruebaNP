package com.example.ingresonapoleon.model.data

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class UserBind(
    var name: String = String(),
    var email: String = String(),
    var phone: String = String()
): Serializable