package com.cheesecake.todo.data.network

import com.cheesecake.todo.data.models.request.TodoPersonalRequest
import com.cheesecake.todo.data.models.request.TodoStatusRequest
import com.cheesecake.todo.data.models.request.TodoTeamRequest
import com.cheesecake.todo.data.repository.identity.LoginCallback
import com.cheesecake.todo.data.repository.identity.SignUpCallback
import com.cheesecake.todo.data.repository.todos.TodoCallback

interface NetworkService {
    fun getPersonalTodos(todoCallback: TodoCallback)
    fun getTeamTodos(todoCallback: TodoCallback)

    fun createPersonalTodo(
        todoPersonalRequest: TodoPersonalRequest,
        todoCallback: TodoCallback
    )

    fun createTeamTodo(
        todoPersonalRequest: TodoTeamRequest,
        todoCallback: TodoCallback
    )

    fun changePersonalTodoStatus(todoStatusRequest: TodoStatusRequest, todoCallback: TodoCallback)
    fun changeTeamTodoStatus(todoStatusRequest: TodoStatusRequest, todoCallback: TodoCallback)

    fun login(username: String, password: String, loginCallback: LoginCallback)

    fun signUp(
        username: String,
        password: String,
        teamId: String,
        signUpCallback: SignUpCallback
    )
}