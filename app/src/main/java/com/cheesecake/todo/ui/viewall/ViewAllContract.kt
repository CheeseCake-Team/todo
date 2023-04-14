package com.cheesecake.todo.ui.viewall

import com.cheesecake.todo.data.models.TodoItem

interface ViewAllContract {
    interface IView {
        fun showTodos(todos: List<TodoItem>)
        fun showError(message: String)
        fun navigateToLoginScreen()
    }

    interface IPresenter {
        fun attachView(view: IView)
        fun detachView()
        fun getTodos(isPersonal: Boolean)
    }
}