package com.cheesecake.todo.data.network

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState

interface NetworkService {
    fun getPersonalTodos(callback: (List<TodoItem>?, String?) -> Unit)
    fun getTeamTodos(callback: (List<TodoItem>?, String?) -> Unit)

    fun createPersonalTodo(
        title: String,
        description: String,
        assignee: String?,
        isPersonal: Boolean,
        token: String,
        callback: (String?) -> Unit
    )

    fun createTeamTodo(
        title: String,
        description: String,
        assignee: String?,
        isPersonal: Boolean,
        token: String,
        callback: (String?) -> Unit
    )

    fun changeTodoStatus(
        todoId: String,
        newStatus: TodoState,
        isPersonal: Boolean,
        token: String,
        callback: (String?) -> Unit
    )

    fun login(
        username: String, password: String, callback: (Pair<String, String>?, String?) -> Unit
    )

    fun signUp(
        username: String, password: String, teamId: String, signUpCallback: (String?) -> Unit
    )
}