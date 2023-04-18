package com.cheesecake.todo.ui.viewall

import com.cheesecake.todo.data.models.TodoItem

interface ViewAllContract {
    interface IView {
        fun showTodos(todos: List<TodoItem>)
        fun showError(message: String)
        fun navigateToLoginScreen()
        fun toggleSelected(position: Int)
    }

    interface IPresenter {
        fun attachView(view: IView,isPersonal: Boolean?)
        fun detachView()
        fun requestAllTodos()
        fun onToggleSelected(position: Int)
    }
}