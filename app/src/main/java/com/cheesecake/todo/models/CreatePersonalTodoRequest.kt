package com.cheesecake.todo.models

data class CreatePersonalTodoRequest(
    val title: String,
    val description: String
)