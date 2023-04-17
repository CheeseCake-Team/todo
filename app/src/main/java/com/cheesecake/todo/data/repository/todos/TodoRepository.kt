package com.cheesecake.todo.data.repository.todos

import com.cheesecake.todo.data.models.TodoState

interface TodoRepository {
    fun getTeamTodos(todoCallback: TodoCallback)
    fun getPersonalTodos(todoCallback: TodoCallback)
    fun createTeamTodo(title: String, description: String, assignee: String, todoCallback: TodoCallback)

    fun createPersonalTodo(title: String, description: String, todoCallback: TodoCallback)

    fun changePersonalTodoStatus(todoId: String, newStatus: TodoState, todoCallback: TodoCallback)

    fun changeTeamTodoStatus(todoId: String, newStatus: TodoState, todoCallback: TodoCallback)

}
