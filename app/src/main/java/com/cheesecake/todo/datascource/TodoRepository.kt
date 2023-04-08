package com.cheesecake.todo.datascource

interface TodoRepository {
    fun getTodos(isPersonal: Boolean, token: String, callback: TodoRepositoryCallback)
    fun createTodo(
        title: String,
        description: String,
        assignee: String?,
        isPersonal: Boolean,
        token: String,
        callback: TodoRepositoryCallback
    )

    fun changeTodoStatus(
        todoId: String,
        newStatus: Int,
        isPersonal: Boolean,
        token: String,
        callback: TodoRepositoryCallback
    )

    fun login(username: String, password: String, callback: TodoRepositoryCallback)
    fun signUp(username: String, password: String, teamId: String, callback: TodoRepositoryCallback)
}
