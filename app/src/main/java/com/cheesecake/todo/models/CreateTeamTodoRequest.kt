package com.cheesecake.todo.models

data class CreateTeamTodoRequest(
    val title: String,
    val description: String,
    val teamId: String
)
