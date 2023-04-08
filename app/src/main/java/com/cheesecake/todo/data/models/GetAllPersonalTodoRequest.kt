package com.cheesecake.todo.data.models

data class GetAllPersonalTodoRequest(
    val allTodoList: List<TodoItem>,
    val isSuccess: Boolean,
)
