package com.cheesecake.todo.models

data class GetAllTeamTodoRequest(
    val allTodoList: List<Todo>,
    val isSuccess: Boolean,
)
