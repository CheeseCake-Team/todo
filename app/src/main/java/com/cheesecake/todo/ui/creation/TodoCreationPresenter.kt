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
        todoRepository.createPersonalTodo(title, description, this)

    }

    fun createTeamTodo(
        title: String,
        description: String,
        assignee: String,
    ) {
        todoRepository.createTeamTodo(title, description, assignee, this)
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