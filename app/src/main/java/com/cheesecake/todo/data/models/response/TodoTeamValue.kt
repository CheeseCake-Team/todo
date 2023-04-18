package com.cheesecake.todo.data.models.response

import com.cheesecake.todo.data.models.TodoItem

data class TodoTeamResponse(
    val value: List<TodoItem>,
    val message: String?,
    val isSuccess: Boolean
)
