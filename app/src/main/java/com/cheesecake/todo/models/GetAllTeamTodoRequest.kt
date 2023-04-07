package com.cheesecake.todo.models

data class GetAllTeamTodoRequest(
    val id: String,
    val title: String,
    val description: String,
    val assignee: String,
    val status: Int,
    val creationTime: Long,
)
