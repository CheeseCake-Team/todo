package com.cheesecake.todo.datascource

import com.cheesecake.todo.network.Todo

interface TodoRepositoryCallback {
    fun onGetTodosSuccess(todos: List<Todo>)
    fun onGetTodosError(error: String)
    fun onCreateTodoSuccess()
    fun onCreateTodoError(error: String)
    fun onChangeTodoStatusSuccess()
    fun onChangeTodoStatusError(error: String)
    fun onLoginSuccess(pair: Pair<String,String>)
    fun onLoginError(error: String)
    fun onSignUpSuccess(pair: Pair<String,String>)
    fun onSignUpError(error: String)
}

