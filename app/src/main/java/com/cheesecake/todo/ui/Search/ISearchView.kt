package com.cheesecake.todo.ui.Search

import com.cheesecake.todo.data.models.TodoItem

interface ISearchView {



    fun onSearchSuccess(results: List<TodoItem>)
    fun showError(error: String)


}