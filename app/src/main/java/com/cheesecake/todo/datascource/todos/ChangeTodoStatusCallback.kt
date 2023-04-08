package com.cheesecake.todo.datascource.todos

interface ChangeTodoStatusCallback {
    fun onChangeTodoStatusSuccess()
    fun onChangeTodoStatusError(error: String)
}

