package com.cheesecake.todo.models

data class PersonalTodoItem(
    val id: String,
    val title: String,
    val description: String,
    val status: Int,
    val creationTime: Long,
)
