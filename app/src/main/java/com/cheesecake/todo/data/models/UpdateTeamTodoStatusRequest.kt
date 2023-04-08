package com.cheesecake.todo.data.models

data class UpdateTeamTodoStatusRequest(
    val id: String,
    val status: Int,
    val isSuccess: Boolean,
)
