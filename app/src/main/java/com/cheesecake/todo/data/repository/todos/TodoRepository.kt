package com.cheesecake.todo.data.repository.todos

import com.cheesecake.todo.data.models.TodoState

interface TodoRepository {
    fun getTodos(isPersonal: Boolean, token: String, callback: TodoCallback)
    fun createTodo(
        title: String,
        description: String,
        assignee: String?,
        isPersonal: Boolean,
        token: String,
        callback: TodoCallback
    )

    fun changeTodoStatus(
        todoId: String,
        newStatus: TodoState,
        isPersonal: Boolean,
        token: String,
        callback: TodoCallback
    )

}
