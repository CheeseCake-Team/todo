package com.cheesecake.todo.ui.viewall

import android.util.Log
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.repository.todos.TodoCallback
import com.cheesecake.todo.data.repository.todos.TodoRepository

private const val TAG = "ViewAllPresenter"

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
        currentList = todos
    }

    override fun onSuccessPersonalTodo(todos: List<TodoItem>?) {
        currentList = todos
    }

    override fun onError(error: String) {
        _view!!.showError(error)
    }

    override fun onToggleSelected(position: Int) {
        Log.d(TAG, position.toString() + "here")
        val todos = currentList!!.filter {
            it.status.value == position
        }
        _view!!.showTodos(todos)
        Log.d(TAG, currentList.toString())
        Log.d(TAG, todos.toString())
    }

}