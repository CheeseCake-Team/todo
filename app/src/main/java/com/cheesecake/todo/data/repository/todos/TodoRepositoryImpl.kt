package com.cheesecake.todo.data.repository.todos

import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.data.network.NetworkService

class TodoRepositoryImpl(
    private val networkDataSource: NetworkService,
    private val sharedPreferencesService: SharedPreferencesService
) : TodoRepository {

    override fun getTodos(isPersonal: Boolean, callback: TodoCallback) {
        val token = sharedPreferencesService.getToken()!!
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
        callback: TodoCallback
    ) {
        val token = sharedPreferencesService.getToken()!!
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
        callback: TodoCallback
    ) {
        val token = sharedPreferencesService.getToken()!!
        networkDataSource.changeTodoStatus(todoId, newStatus, isPersonal, token) { error ->
            if (error != null) {
                callback.onError(error)
            } else {
                callback.onSuccess()
            }
        }
    }

}
