package com.cheesecake.todo.models

data class UpdatePersonalTodoStatusRequest(
    val id: String,
    val status: Int
)