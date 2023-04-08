package com.cheesecake.todo.datascource

import com.cheesecake.todo.network.INetworkService

class TodoRepositoryImpl(private val networkDataSource: INetworkService) : TodoRepository {

    override fun getTodos(isPersonal: Boolean, token: String, callback: TodoRepositoryCallback) {
        networkDataSource.getTodos(isPersonal, token) { todos, error ->
            if (error != null) {
                callback.onGetTodosError(error)
            } else {
                callback.onGetTodosSuccess(todos!!)
            }
        }
    }

    override fun createTodo(
        title: String,
        description: String,
        assignee: String?,
        isPersonal: Boolean,
        token: String,
        callback: TodoRepositoryCallback
    ) {
        networkDataSource.createTodo(title, description, assignee, isPersonal, token) { error ->
            if (error != null) {
                callback.onCreateTodoError(error)
            } else {
                callback.onCreateTodoSuccess()
            }
        }
    }

    override fun changeTodoStatus(
        todoId: String,
        newStatus: Int,
        isPersonal: Boolean,
        token: String,
        callback: TodoRepositoryCallback
    ) {
        networkDataSource.changeTodoStatus(todoId, newStatus, isPersonal, token) { error ->
            if (error != null) {
                callback.onChangeTodoStatusError(error)
            } else {
                callback.onChangeTodoStatusSuccess()
            }
        }
    }

    override fun login(username: String, password: String, callback: TodoRepositoryCallback) {
        networkDataSource.login(username, password) { pair, error ->
            if (error != null) {
                callback.onLoginError(error)
            } else {
                callback.onLoginSuccess(pair!!)
            }
        }
    }

    override fun signUp(
        username: String,
        password: String,
        teamId: String,
        callback: TodoRepositoryCallback
    ) {
        networkDataSource.signUp(username, password, teamId) { token, error ->
            if (error != null) {
                callback.onSignUpError(error)
            } else {
                callback.onSignUpSuccess(token!!)
            }
        }
    }
}
