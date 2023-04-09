package com.cheesecake.todo.data.repository.todos

import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.data.network.NetworkService

class TodoRepositoryImpl(private val networkDataSource: NetworkService) : TodoRepository {

    override fun getTodos(isPersonal: Boolean, token: String, callback: TodoCallback) {
        networkDataSource.getTodos(isPersonal, token) { todos, error ->
            if (error != null) {
                callback.onError(error)
            } else {
                callback.onSuccess(todos!!)
            }
        }
    }

    override fun createTodo(
        title: String,
        description: String,
        assignee: String?,
        isPersonal: Boolean,
        token: String,
        callback: TodoCallback
    ) {
        networkDataSource.createTodo(title, description, assignee, isPersonal, token) { error ->
            if (error != null) {
                callback.onError(error)
            } else {
                callback.onSuccess()
            }
        }
    }

    override fun changeTodoStatus(
        todoId: String,
        newStatus: TodoState,
        isPersonal: Boolean,
        token: String,
        callback: TodoCallback
    ) {
        networkDataSource.changeTodoStatus(todoId, newStatus, isPersonal, token) { error ->
            if (error != null) {
                callback.onError(error)
            } else {
                callback.onSuccess()
            }
        }
    }

}
