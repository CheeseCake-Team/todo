package com.cheesecake.todo.datascource.todos

interface CreateTodoCallback {
    fun onCreateTodoSuccess()
    fun onCreateTodoError(error: String)
}