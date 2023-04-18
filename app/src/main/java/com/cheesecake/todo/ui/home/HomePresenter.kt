package com.cheesecake.todo.ui.home

import com.cheesecake.todo.data.models.HomeItem
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.data.repository.todos.TodoCallback
import com.cheesecake.todo.data.repository.todos.TodoRepository

class HomePresenter(private val todoRepository: TodoRepository) : TodoCallback {
    private var homeView: HomeView? = null
    private val allTodos= mutableListOf<TodoItem>()
    private lateinit var personalTodos: List<TodoItem>
    private lateinit var teamTodos: List<TodoItem>

    fun attachView(view: HomeView) {
        homeView = view
    }

    fun detachView() {
        homeView = null
    }

    override fun onSuccess(todos: List<TodoItem>?) {
        if (todos != null) {
            allTodos.addAll(todos)
        }
        if (::personalTodos.isInitialized && ::teamTodos.isInitialized) {
            val homeItem = HomeItem(
                personalTodoPercentage = getPersonalTodoPercentage(),
                personalProgressPercentage = getPersonalProgressPercentage(),
                personalDonePercentage = getPersonalDonePercentage(),
                teamDonePercentage = getTeamDonePercentage(),
                personalTodos = personalTodos,
                teamTodo = teamTodos
            )
            homeView?.initHomeList(homeItem)
        } else {
            personalTodos = allTodos.filter { it.assignee == null }
            teamTodos = allTodos.filter { it.assignee != null }
        }
    }

    override fun onError(error: String) {
        homeView!!.showError(error)
    }

    fun initTodos() {
        todoRepository.getTeamTodos(this)
        todoRepository.getPersonalTodos(this)
    }

    private fun getPersonalTodoPercentage(): Int {
        val todo = personalTodos.filter { it.status == TodoState.TODO }
        return todo.size.div(5)

    }

    private fun getPersonalProgressPercentage(): Int {
        val progress = personalTodos.filter { it.status == TodoState.IN_PROGRESS }
        return progress.size.div(5)

    }

    private fun getPersonalDonePercentage(): Int {
        val done = personalTodos.filter { it.status == TodoState.DONE }
        return done.size.div(5)
    }

    private fun getTeamDonePercentage(): Int {
        val done = teamTodos.filter { it.status == TodoState.DONE }
        return done.size.div(5)
    }


}