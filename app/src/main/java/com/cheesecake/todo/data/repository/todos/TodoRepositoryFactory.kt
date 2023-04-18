package com.cheesecake.todo.data.repository.todos

interface TodoRepositoryFactory {
    fun createTodoRepository(): TodoRepository
}