package com.cheesecake.todo.ui.viewall

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.repository.todos.TodoCallback
import com.cheesecake.todo.data.repository.todos.TodoRepository

class ViewAllPresenter(private val todoRepository: TodoRepository) :
    ViewAllContract.IPresenter, TodoCallback {

    private var _view: ViewAllContract.IView? = null
    private var _isPersonal: Boolean? = null
    private var currentList: List<TodoItem>? = null

    override fun attachView(view: ViewAllContract.IView, isPersonal: Boolean?) {
        this._view = view
        this._isPersonal = isPersonal
    }



    override fun detachView() {
        this.apply {
            _view = null
            currentList = null
            _isPersonal = null
        }
    }

    override fun requestAllTodos() {
        when (_isPersonal) {
            true -> todoRepository.getPersonalTodos(this)
            false -> todoRepository.getTeamTodos(this)
            else -> throw IllegalArgumentException("Cannot determine type of todos. _isPersonal cannot be null.")
        }
    }

    override fun onSuccessTeamTodo(todos: List<TodoItem>?) {
        _view!!.showTodos(todos!!)
    }

    override fun onSuccessPersonalTodo(todos: List<TodoItem>?) {
        _view!!.showTodos(todos!!)
    }

    override fun onError(error: String) {
        _view!!.showError(error)
    }

    override fun onToggleSelected(position: Int) {
        filterAndUpdateList(position)
    }

    private fun filterAndUpdateList(type: Int) {
        val todos = currentList!!.filter { todo ->
            todo.status.value == type
        }
        _view!!.showTodos(todos)
    }
}