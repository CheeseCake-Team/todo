package com.cheesecake.todo.models

data class GetAllPersonalTodoRequest(
    val allTodoList: List<TodoItem>,
    val isSuccess: Boolean,
)
