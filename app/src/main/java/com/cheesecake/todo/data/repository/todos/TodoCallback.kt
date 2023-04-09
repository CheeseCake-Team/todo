package com.cheesecake.todo.data.repository.todos

import com.cheesecake.todo.data.models.TodoItem

interface TodoCallback {
    fun onSuccess(todos: List<TodoItem>? = null)
    fun onError(error: String)
}