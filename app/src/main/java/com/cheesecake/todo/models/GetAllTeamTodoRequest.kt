package com.cheesecake.todo.models

data class GetAllTeamTodoRequest(
    val allTodoList: List<TodoItem>,
    val isSuccess: Boolean,
)
