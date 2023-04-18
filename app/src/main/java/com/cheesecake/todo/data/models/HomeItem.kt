package com.cheesecake.todo.data.models

data class HomeItem (
    val personalTodoPercentage: Int,
    val personalProgressPercentage: Int,
    val personalDonePercentage: Int,
    val teamDonePercentage: Int,
    val personalTodos: List<TodoItem>,
    val teamTodo: List<TodoItem>
)