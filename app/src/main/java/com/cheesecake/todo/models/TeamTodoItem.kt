package com.cheesecake.todo.models

data class TeamTodoItem(
    val id: String,
    val title: String,
    val description: String,
    val assignee: String,
    val status: Int,
    val creationTime: Long,
)
