package com.cheesecake.todo.ui.viewall

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.repository.todos.TodoCallback
import com.cheesecake.todo.data.repository.todos.TodoRepository
import com.cheesecake.todo.ui.base.BasePresenter


class TodosPresenter(
    private val todoRepository: TodoRepository,
    private val ViewAllTodosView: TodosContract.IView,
    private val _isPersonal: Boolean
) :
    TodosContract.IPresenter,
    BasePresenter<TodoRepository, TodosContract.IView>(todoRepository, ViewAllTodosView),
    TodoCallback {


    private lateinit var currentList: List<TodoItem>

    override fun requestAllTodos() {
        if (repository.isTokenValid()) {
            when (_isPersonal) {
                true -> todoRepository.getPersonalTodos(this)
                false -> todoRepository.getTeamTodos(this)
            }
        } else view.navigateToLoginScreen()

    }

    override fun onSuccessTeamTodo(todos: List<TodoItem>?) {
        if (todos != null) {
            currentList = todos
        }
//        todos?.let { ViewAllTodosView.showTodos(it) }


    }

    override fun onSuccessPersonalTodo(todos: List<TodoItem>?) {
        if (todos != null) {
            currentList = todos
        }
//        todos?.let { ViewAllTodosView.showTodos(it) }

    }

    override fun onError(error: String) {
        ViewAllTodosView.showError(error)
    }

    override fun onToggleSelected(position: Int) {
        currentList.let {
            val filteredList = it.filter { item ->
                item.status.value == position
            }
            ViewAllTodosView.showTodos(filteredList)
        }
    }

}