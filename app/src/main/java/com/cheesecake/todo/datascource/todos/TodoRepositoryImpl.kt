package com.cheesecake.todo.datascource.todos

import com.cheesecake.todo.network.INetworkService

class TodoRepositoryImpl(private val networkDataSource: INetworkService) : TodoRepository {

    override fun getTodos(isPersonal: Boolean, token: String, callback: GetTodosCallback) {
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
        callback: CreateTodoCallback
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
        callback: ChangeTodoStatusCallback
    ) {
        networkDataSource.changeTodoStatus(todoId, newStatus, isPersonal, token) { error ->
            if (error != null) {
                callback.onChangeTodoStatusError(error)
            } else {
                callback.onChangeTodoStatusSuccess()
            }
        }
    }

}
