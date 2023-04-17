package com.cheesecake.todo.data.models.request

import com.cheesecake.todo.data.models.TodoState

data class TodoStatus(
    val todoId: String,
    val newStatus: TodoState,
    val isPersonal: Boolean,
    val token: String,
)
