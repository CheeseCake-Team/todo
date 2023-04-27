package com.cheesecake.todo.ui.home

import android.util.Log
import com.cheesecake.todo.data.models.Tag
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.repository.todos.TodoRepositoryImpl
import com.cheesecake.todo.ui.base.BasePresenter

class HomePresenter(
    private val todoRepository: TodoRepositoryImpl, homeView: HomeView
) : BasePresenter<HomeView>(todoRepository, homeView) {
    private lateinit var homeList: MutableList<DataItem>
    private fun onSuccessPersonalTodo(response: BaseResponse<List<TodoItem>>) {
        if (response.isSuccess) {
            val tag = DataItem.TagItem(Tag(1, 1, "Recently Personal", response.value))
            homeList.add(tag)
            homeList = homeList.sortedBy { it.rank }.toMutableList()
            if (homeList.size == 2) view.initHomeList(homeList)

        } else view.showError(response.message)
    }

    private fun onSuccessTeamTodo(response: BaseResponse<List<TodoItem>>) {
        if (response.isSuccess) {
            val tag = DataItem.TagItem(Tag(2, 2, "Recently Team", response.value))
            homeList.add(tag)
            homeList = homeList.sortedBy { it.rank }.toMutableList()
            if (homeList.size == 2) view.initHomeList(homeList)

        } else view.showError(response.message)
    }

    private fun onError(e: Throwable) {
        view.showError(e.toString())
    }

    fun initTodos() {
        if (todoRepository.isTokenValid()) {
            homeList = mutableListOf()
            todoRepository.getPersonalTodos(::onSuccessPersonalTodo, ::onError)
            todoRepository.getTeamTodos(::onSuccessTeamTodo, ::onError)
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