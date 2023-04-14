package com.cheesecake.todo.data.repository.todos

import com.cheesecake.todo.data.models.TodoState

interface TodoRepository {
    fun getTodos(isPersonal: Boolean, callback: TodoCallback)
    fun createTodo(
        title: String,
        description: String,
        assignee: String?,
        isPersonal: Boolean,
        callback: TodoCallback
    )

    fun changeTodoStatus(
        todoId: String,
        newStatus: TodoState,
        isPersonal: Boolean,
        callback: TodoCallback
    )

}
