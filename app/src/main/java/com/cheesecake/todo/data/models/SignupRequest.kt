package com.cheesecake.todo.data.models

data class SignupRequest(
    val username: String,
    val password: String,
    val teamId: String
)
