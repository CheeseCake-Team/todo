package com.cheesecake.todo.ui.home

import android.util.Log
import com.cheesecake.todo.data.models.Tag
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.repository.todos.TodoCallback
import com.cheesecake.todo.data.repository.todos.TodoRepository
import com.cheesecake.todo.ui.base.BasePresenter

class HomePresenter(
    todoRepository: TodoRepository,
    homeView: HomeView
) : BasePresenter<TodoRepository, HomeView>(todoRepository, homeView), TodoCallback {
    private lateinit var homeList: MutableList<DataItem>
    override fun onSuccessTeamTodo(todos: List<TodoItem>?) {
        val tag = DataItem.TagItem(Tag(2, 2, "Recently Team", todos!!))
        homeList.add(tag)
        homeList = homeList.sortedBy { it.rank }.toMutableList()
        if (homeList.size == 2)
            view.initHomeList(homeList)
    }

    override fun onSuccessPersonalTodo(todos: List<TodoItem>?) {
        val tag = DataItem.TagItem(Tag(1, 1, "Recently Personal", todos!!))
        homeList.add(tag)
        homeList = homeList.sortedBy { it.rank }.toMutableList()
        if (homeList.size == 2)
            view.initHomeList(homeList)
    }

    override fun onError(error: String) {
        view.showError(error)
    }

    fun initTodos() {
        if (repository.isTokenValid()) {
            homeList = mutableListOf()
            repository.getPersonalTodos(this)
            repository.getTeamTodos(this)
        } else view.navigateToLoginScreen()
    }

    fun search(text: String): List<TodoItem> {
        return if (homeList.size == 2) {
            val firstSearchResult = (homeList[0] as DataItem.TagItem).tag.todos.filter {
                it.title.contains(text) || it.description.contains(text)
            }
            Log.d("onQueryTextChange: ", firstSearchResult.toString())
            val secondSearchResult = (homeList[1] as DataItem.TagItem).tag.todos.filter {
                it.title.contains(text) || it.description.contains(text)
            }
            Log.d("onQueryTextChange: ", secondSearchResult.toString())
            firstSearchResult + secondSearchResult
        } else emptyList()
    }
}