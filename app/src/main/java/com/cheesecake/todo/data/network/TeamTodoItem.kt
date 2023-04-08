package com.cheesecake.todo.data.network

data class Todo(
    val id: String,
    val title: String,
    val description: String,
    val assignee: String? = null,
    val status: Int,
    val creationTime: String,
)
