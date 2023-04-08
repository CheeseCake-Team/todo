package com.cheesecake.todo.data.repository.todos

interface ChangeTodoStatusCallback {
    fun onChangeTodoStatusSuccess()
    fun onChangeTodoStatusError(error: String)
}

