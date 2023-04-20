package com.cheesecake.todo.data.models.request

import com.cheesecake.todo.data.models.TodoState

data class TodoStatusRequest(
    val todoId: String,
    val newStatus: TodoState,
)
