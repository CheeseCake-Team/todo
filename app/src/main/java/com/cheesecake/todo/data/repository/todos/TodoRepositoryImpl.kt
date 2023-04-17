package com.cheesecake.todo.data.repository.todos

import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.data.models.request.TodoPersonalRequest
import com.cheesecake.todo.data.models.request.TodoStatus
import com.cheesecake.todo.data.models.request.TodoTeamRequest
import com.cheesecake.todo.data.network.NetworkService

class TodoRepositoryImpl(
    private val networkDataSource: NetworkService,
    private val sharedPreferencesService: SharedPreferencesService
) : TodoRepository {

    override fun getTeamTodos(todoCallback: TodoCallback) {
        val token = sharedPreferencesService.getToken()!!
        networkDataSource.getTeamTodos(todoCallback)
    }

    override fun getPersonalTodos(todoCallback: TodoCallback) {
        val token = sharedPreferencesService.getToken()!!
        networkDataSource.getPersonalTodos(todoCallback)
    }

    override fun createTeamTodo(
        title: String, description: String, assignee: String, todoCallback: TodoCallback
    ) {
        val todoTeamRequest = TodoTeamRequest(title, description, assignee)
        networkDataSource.createTeamTodo(todoTeamRequest, todoCallback)
    }

    override fun createPersonalTodo(
        title: String, description: String, todoCallback: TodoCallback
    ) {
        val todoPersonalRequest = TodoPersonalRequest(title, description)
        networkDataSource.createPersonalTodo(todoPersonalRequest, todoCallback)
    }

    override fun changeTeamTodoStatus(
        todoId: String, newStatus: TodoState, todoCallback: TodoCallback
    ) {
        networkDataSource.createTodo(title, description, assignee, isPersonal, token)
        { error ->
            if (error != null) {
                callback.onError(error)
            } else {
                callback.onSuccess()
            }
        }
        val todoStatus = TodoStatus(todoId, newStatus)
        networkDataSource.changeTeamTodoStatus(todoStatus, todoCallback)
    }

    override fun changePersonalTodoStatus(
        todoId: String, newStatus: TodoState, todoCallback: TodoCallback
    ) {
        val todoStatus = TodoStatus(todoId, newStatus)
        networkDataSource.changePersonalTodoStatus(todoStatus, todoCallback)
    }

}
