package com.cheesecake.todo.data.repository.todos

import com.cheesecake.todo.data.network.Todo

interface GetTodosCallback {
    fun onGetTodosSuccess(todos: List<Todo>)
    fun onGetTodosError(error: String)
}