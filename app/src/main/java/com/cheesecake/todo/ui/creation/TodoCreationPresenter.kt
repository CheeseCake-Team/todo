package com.cheesecake.todo.ui.creation

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.repository.todos.TodoCallback
import com.cheesecake.todo.data.repository.todos.TodoRepository
import com.cheesecake.todo.ui.base.BasePresenter


class TodoCreationPresenter(
    private val todoRepository: TodoRepository,
    private val toDoCreationView: TodoCreationFragment
) : BasePresenter<TodoRepository, TodoCreationFragment>(todoRepository, toDoCreationView),
    TodoCallback {


    fun createPersonalTodo(
        title: String,
        description: String,
    ) {
        if (repository.isTokenValid()) {
            todoRepository.createPersonalTodo(title, description, this)
        } else
            view.navigateToLoginScreen()

    }

    fun createTeamTodo(
        title: String,
        description: String,
        assignee: String,
    ) {
        if (repository.isTokenValid()) {
            todoRepository.createTeamTodo(title, description, assignee, this)
        } else
            view.navigateToLoginScreen()

    }


    override fun onSuccessTeamTodo(todos: List<TodoItem>?) {
        toDoCreationView.showDialog()

    }

    override fun onSuccessPersonalTodo(todos: List<TodoItem>?) {
        toDoCreationView.showDialog()
    }

    override fun onError(error: String) {
        toDoCreationView.showError(error)
    }


}