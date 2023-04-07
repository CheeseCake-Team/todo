package com.cheesecake.todo.models

data class SignupRequest(
    val username: String,
    val password: String,
    val teamId: String
)
