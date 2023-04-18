package com.cheesecake.todo.ui.home

import com.cheesecake.todo.data.models.Tag
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.repository.todos.TodoCallback
import com.cheesecake.todo.data.repository.todos.TodoRepository

class HomePresenter(private val todoRepository: TodoRepository) : TodoCallback {
    private var homeView: HomeView? = null
    private val homeList = mutableListOf<DataItem>()

    fun attachView(view: HomeView) {
        homeView = view
    }

    fun detachView() {
        homeView = null
    }

    override fun onSuccessTeamTodo(todos: List<TodoItem>?) {
        val tag = DataItem.TagItem(Tag(2, "Recently Team", todos!!))
        homeList.add(tag)
        homeView?.initHomeList(homeList)
    }

    override fun onSuccessPersonalTodo(todos: List<TodoItem>?) {
        val tag = DataItem.TagItem(Tag(1, "Recently Personal", todos!!))
        homeList.add(tag)
    }

    override fun onError(error: String) {
        homeView!!.showError(error)
    }

    fun initTodos() {
        todoRepository.getPersonalTodos(this)
        todoRepository.getTeamTodos(this)
    }

}