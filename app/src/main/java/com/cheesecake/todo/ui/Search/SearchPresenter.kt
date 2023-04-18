//package com.cheesecake.todo.ui.Search
//
//import com.cheesecake.todo.data.models.TodoItem
//import com.cheesecake.todo.data.network.NetworkServiceImpl
//import com.cheesecake.todo.data.repository.todos.TodoRepository
//import com.google.android.material.search.SearchView
//
//class SearchPresenter (
//    private val todoRepository: TodoRepository,
//    private val networkServiceImpl: NetworkServiceImpl
//)
//{
//
//    private var searchView: SearchView? = null
//
//
//    @Override fun onSuccess(todos: List<TodoItem>?) {
//        searchView!!.getSearchReasult()
//    }
//
//
//    fun getSearchReasult(){
//        val result=networkServiceImpl.getTodos()
//        searchView?.onSearchSuccess(result)
//    }
//
//    fun attachView(view: SearchView) {
//        searchView = view
//    }
//
//    fun detachView() {
//        searchView = null
//    }
//
//
//}