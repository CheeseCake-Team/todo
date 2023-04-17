package com.cheesecake.todo.data.network

sealed class ApiResult {
    data class Success<T>(val responseBody: T) : ApiResult()
    data class Failure(val errorMessage: String) : ApiResult()
}