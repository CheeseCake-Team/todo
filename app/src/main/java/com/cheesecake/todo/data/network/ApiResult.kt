package com.cheesecake.todo.data.network

sealed class ApiResult {
    data class Success(val responseBody: String) : ApiResult()
    data class Failure(val errorMessage: String) : ApiResult()
}