package com.cheesecake.todo.datascource.todos

import com.cheesecake.todo.network.Todo

interface GetTodosCallback {
    fun onGetTodosSuccess(todos: List<Todo>)
    fun onGetTodosError(error: String)
}