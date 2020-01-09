package com.example.ingresonapoleon.services

sealed class ConnectionResponse {
    class OnSuccess(val result: String) : ConnectionResponse()
    object OnFailure : ConnectionResponse()
}