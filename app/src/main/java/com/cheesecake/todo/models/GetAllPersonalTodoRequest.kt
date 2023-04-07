package com.cheesecake.todo.models

data class GetAllPersonalTodoRequest(
    val allTodoList: List<PersonalTodoItem>,
    val isSuccess: Boolean,
)
