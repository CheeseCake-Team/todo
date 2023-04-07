package com.cheesecake.todo.models

data class TodoItem(
    val id: String,
    val title: String,
    val description: String,
    val status: Int
)
