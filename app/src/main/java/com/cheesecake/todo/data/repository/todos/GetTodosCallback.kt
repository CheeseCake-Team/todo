package com.cheesecake.todo.data.repository.todos

import com.cheesecake.todo.data.models.TodoItem

interface GetTodosCallback {
    fun onGetTodosSuccess(todos: List<TodoItem>)
    fun onGetTodosError(error: String)
}