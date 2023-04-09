package com.cheesecake.todo.data.network

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState

interface NetworkService {
    fun getTodos(isPersonal: Boolean, token: String, callback: (List<TodoItem>?, String?) -> Unit)

    fun createTodo(
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
        username: String,
        password: String,
        teamId: String,
        callback: (Pair<String, String>?, String?) -> Unit
    )
}