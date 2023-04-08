package com.cheesecake.todo.data.models

data class LoginRequest(
    val username: String,
    val token: String,
    val expiration: Long,
    val isSuccess: Boolean,
)
