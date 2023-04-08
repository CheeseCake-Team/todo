package com.cheesecake.todo.models

data class GetAllTeamTodoRequest(
    val allTodoList: List<TeamTodoItem>,
    val isSuccess: Boolean,
)
