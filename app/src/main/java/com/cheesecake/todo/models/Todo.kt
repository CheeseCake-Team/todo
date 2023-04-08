package com.cheesecake.todo.models

data class Todo(
    val id: String,
    val title: String,
    val description: String,
    val assignee: String? = null,
    val status: Int,
    val creationTime: Long,
)


