package com.cheesecake.todo.models

data class UpdateTeamTodoStatusRequest(
    val id: String,
    val status: Int,
    val isSuccess: Boolean,
)
