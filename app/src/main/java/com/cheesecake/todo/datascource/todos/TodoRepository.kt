package com.cheesecake.todo.datascource.todos

interface TodoRepository {
    fun getTodos(isPersonal: Boolean, token: String, callback: GetTodosCallback)
    fun createTodo(
        title: String,
        description: String,
        assignee: String?,
        isPersonal: Boolean,
        token: String,
        callback: CreateTodoCallback
    )

    fun changeTodoStatus(
        todoId: String,
        newStatus: Int,
        isPersonal: Boolean,
        token: String,
        callback: ChangeTodoStatusCallback
    )

}
