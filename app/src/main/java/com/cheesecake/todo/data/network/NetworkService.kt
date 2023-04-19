package com.cheesecake.todo.data.network

import com.cheesecake.todo.data.models.request.TodoPersonalRequest
import com.cheesecake.todo.data.models.request.TodoStatusRequest
import com.cheesecake.todo.data.models.request.TodoTeamRequest

interface NetworkService {
    fun getPersonalTodos(responseCallback: ResponseCallback)
    fun getTeamTodos(responseCallback: ResponseCallback)

    fun createPersonalTodo(
        todoPersonalRequest: TodoPersonalRequest, responseCallback: ResponseCallback
    )

    fun createTeamTodo(
        todoPersonalRequest: TodoTeamRequest, responseCallback: ResponseCallback
    )

    fun updatePersonalTodoStatus(
        todoStatusRequest: TodoStatusRequest, responseCallback: ResponseCallback
    )

    fun updateTeamTodoStatus(
        todoStatusRequest: TodoStatusRequest, responseCallback: ResponseCallback
    )

    fun login(username: String, password: String, responseCallback: ResponseCallback)

    fun signUp(
        username: String, password: String, teamId: String, responseCallback: ResponseCallback
    )
}