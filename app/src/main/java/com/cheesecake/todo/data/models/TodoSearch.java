package com.cheesecake.todo.data.models;

import java.util.List;

public class TodoSearch(private val todoItem : List<TodoItem>){

    private var searchedtodos = todoItem

    fun searchByName(name: String) = TodoSearch(searchedtodos).apply {
        searchedtodos =
                searchedtodos.filter{ it.title.contains(name) }
    }
    fun searchInDescription(descriptionContent: List<String>) = TodoSearch(searchedtodos).apply {
        searchedtodos =
                searchedtodos.filter { it.description.containsAll(descriptionContent) }
    }
    fun getSearchedTodos(): List<TodoItem> = searchedtodos
}
