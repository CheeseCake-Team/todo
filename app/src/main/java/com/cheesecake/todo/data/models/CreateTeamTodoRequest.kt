package com.cheesecake.todo.data.models

data class CreateTeamTodoRequest(
    val title: String,
    val description: String,
    val teamId: String
)
