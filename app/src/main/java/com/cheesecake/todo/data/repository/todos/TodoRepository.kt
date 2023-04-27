package com.cheesecake.todo.data.repository.todos

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.data.models.response.BaseResponse

interface TodoRepository {
    fun getTeamTodos(
        onSuccess: (BaseResponse<List<TodoItem>>) -> Unit, onError: (e: Throwable) -> Unit
    )

    fun getPersonalTodos(
        onSuccess: (BaseResponse<List<TodoItem>>) -> Unit, onError: (e: Throwable) -> Unit
    )

    fun createTeamTodo(
        title: String,
        description: String,
        assignee: String,
        onSuccess: (BaseResponse<TodoItem>) -> Unit,
        onError: (e: Throwable) -> Unit
    )

    fun createPersonalTodo(
        title: String,
        description: String,
        onSuccess: (BaseResponse<TodoItem>) -> Unit,
        onError: (e: Throwable) -> Unit
    )

    fun updatePersonalTodoStatus(
        todoId: String,
        newStatus: TodoState,
        onSuccess: (BaseResponse<String>) -> Unit,
        onError: (e: Throwable) -> Unit
    )

    fun updateTeamTodoStatus(
        todoId: String,
        newStatus: TodoState,
        onSuccess: (BaseResponse<String>) -> Unit,
        onError: (e: Throwable) -> Unit
    )

    fun isTokenValid(): Boolean
    fun formattedTime(expiry: String): Long

}
