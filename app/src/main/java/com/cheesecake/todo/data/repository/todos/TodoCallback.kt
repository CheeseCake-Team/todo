package com.cheesecake.todo.data.repository.todos

import com.cheesecake.todo.data.models.TodoItem

interface TodoCallback {
    fun onSuccessTeamTodo(todos: List<TodoItem>? = null)
    fun onSuccessPersonalTodo(todos: List<TodoItem>? = null)
    fun onError(error: String)
}