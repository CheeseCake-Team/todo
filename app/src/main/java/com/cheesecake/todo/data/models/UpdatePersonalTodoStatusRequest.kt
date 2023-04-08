package com.cheesecake.todo.data.models

data class UpdatePersonalTodoStatusRequest(
    val id: String,
    val status: Int,
    val isSuccess: Boolean,
)