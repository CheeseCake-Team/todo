package com.cheesecake.todo.data.models

data class Tag(
    val id: Int,
    val title: String,
    val todos: List<TodoItem>
)
