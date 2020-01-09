package com.example.ingresonapoleon.services

sealed class ModelResponse {
    class OnSuccessApi(val result: Any) : ModelResponse()
    class OnSuccessDB(val result: Any) : ModelResponse()
    class OnError(val error: String) : ModelResponse()
}