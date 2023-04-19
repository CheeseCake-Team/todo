package com.cheesecake.todo.ui.createtodo

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.repository.todos.TodoCallback
import com.cheesecake.todo.data.repository.todos.TodoRepository
import com.cheesecake.todo.ui.home.HomeView


class CreateTodoPresenter(private val todoRepository: TodoRepository) : TodoCallback {

    var createView:CreateView ?= null


    fun toDoForPersonal(
        title: String,
        description: String,
        assignee: String,
    ) {
        todoRepository.createTeamTodo(title,description,assignee,this)

    }

    fun toDoForTeam(
        title: String,
        description: String,
        assignee: String,
    ) {
        todoRepository.createTeamTodo(title,description,assignee,this)
    }

    fun attachView(view: CreateView) {
        createView = view
    }

    fun detachView() {
        createView = null
    }

    override fun onSuccessTeamTodo(todos: List<TodoItem>?) {
        createView?.showDialog()

    }

    override fun onSuccessPersonalTodo(todos: List<TodoItem>?) {
        createView?.showDialog()
    }

    override fun onError(error: String) {
        createView?.showError(error)
    }


}