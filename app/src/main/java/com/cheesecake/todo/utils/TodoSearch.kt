package com.cheesecake.todo.utils

import com.cheesecake.todo.data.models.TodoItem


    class TodoSearch(private val todoItem : List<TodoItem>){


        private var searchedtodos = todoItem

        fun searchByName(name: String) = TodoSearch(searchedtodos).apply {
            searchedtodos =
                searchedtodos.filter{ it.title.contains(name) }
        }

        fun getSearchedTodos(): List<TodoItem> = searchedtodos


}