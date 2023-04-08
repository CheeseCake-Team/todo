package com.cheesecake.todo.data.models

data class GetAllTeamTodoRequest(
    val allTodoList: List<TodoItem>,
    val isSuccess: Boolean,
)
