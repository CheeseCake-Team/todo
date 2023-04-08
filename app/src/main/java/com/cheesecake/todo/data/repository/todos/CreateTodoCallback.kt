package com.cheesecake.todo.data.repository.todos

interface CreateTodoCallback {
    fun onCreateTodoSuccess()
    fun onCreateTodoError(error: String)
}