package com.cheesecake.todo.data.network

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.data.models.request.TodoPersonalRequest
import com.cheesecake.todo.data.models.request.TodoStatus
import com.cheesecake.todo.data.models.request.TodoTeamRequest

interface NetworkService {
    fun getPersonalTodos(callback: (List<TodoItem>?, String?) -> Unit)
    fun getTeamTodos(callback: (List<TodoItem>?, String?) -> Unit)

    fun createPersonalTodo(
        todoPersonalRequest: TodoPersonalRequest,
        callback: (String?) -> Unit
    )
    fun createTeamTodo(
        todoPersonalRequest: TodoTeamRequest,
        callback: (String?) -> Unit
    )

    fun changePersonalTodoStatus(todoStatus: TodoStatus, callback: (String?) -> Unit)
    fun changeTeamTodoStatus(todoStatus: TodoStatus, callback: (String?) -> Unit)

    fun login(
        username: String, password: String, callback: (Pair<String, String>?, String?) -> Unit
    )

    fun signUp(
        username: String, password: String, teamId: String, signUpCallback: (String?) -> Unit
    )
}