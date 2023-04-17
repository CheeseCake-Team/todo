package com.cheesecake.todo.data.models.response

data class TodoPersonalValue(
    val id: String,
    val title: String,
    val description: String,
    val status: Int,
    val creationTime: String
)
