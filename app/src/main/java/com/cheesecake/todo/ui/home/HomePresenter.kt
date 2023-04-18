package com.cheesecake.todo.ui.home

import android.util.Log
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
        if (homeList.size == 2)
            homeView?.initHomeList(homeList)
    }

    override fun onSuccessPersonalTodo(todos: List<TodoItem>?) {
        val tag = DataItem.TagItem(Tag(1, "Recently Personal", todos!!))
        homeList.add(tag)
        if (homeList.size == 2)
            homeView?.initHomeList(homeList)
    }

    override fun onError(error: String) {
        homeView!!.showError(error)
    }

    fun initTodos() {
        if (todoRepository.isTokenValid()) {
            todoRepository.getPersonalTodos(this)
            todoRepository.getTeamTodos(this)
        } else homeView?.navigateToLoginScreen()
    }

    fun search(text: String):List<TodoItem> {
        return if (homeList.size > 2) {
            val firstSearchResult = (homeList[0] as DataItem.TagItem).tag.todos.filter { it.title.contains(text)
                    || it.description.contains(text)
            }
            Log.d("onQueryTextChange: ", firstSearchResult.toString())
            val secondSearchResult = (homeList[1] as DataItem.TagItem).tag.todos.filter { it.title.contains(text)
                    || it.description.contains(text)
            }
            Log.d("onQueryTextChange: ", secondSearchResult.toString())
            firstSearchResult + secondSearchResult
        } else emptyList()
    }

}