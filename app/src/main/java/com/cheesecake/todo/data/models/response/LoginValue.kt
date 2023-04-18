package com.cheesecake.todo.data.models.response


data class LoginValue(
    val token: String,
    val expireAt: String
)

data class LoginResponse(
    val value: LoginValue?,
    val message: String?,
    val isSuccess: Boolean
)

