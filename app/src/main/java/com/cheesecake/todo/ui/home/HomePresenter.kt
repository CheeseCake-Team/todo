package com.cheesecake.todo.ui.home

import com.cheesecake.todo.data.models.HomeItem
import com.cheesecake.todo.data.models.Tag
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.data.repository.todos.TodoCallback
import com.cheesecake.todo.data.repository.todos.TodoRepository

class HomePresenter(private val todoRepository: TodoRepository) : TodoCallback {
    private var homeView: HomeView? = null
    private val allTodos = mutableListOf<TodoItem>()
    private val personalTodosList = mutableListOf<TodoItem>()
    private val homeList = mutableListOf<DataItem>()
    private lateinit var personalTodos: List<TodoItem>
    private lateinit var teamTodos: List<TodoItem>

    fun attachView(view: HomeView) {
        homeView = view
    }

    fun detachView() {
        homeView = null
    }

    override fun onSuccessTeamTodo(todos: List<TodoItem>?) {
        val tag = DataItem.TagItem(Tag(2, "Recently Team", todos!!))
//        allTodos.addAll(todos)
//        if (::personalTodos.isInitialized && ::teamTodos.isInitialized) {
//            val homeItem = HomeItem(
//                personalTodoPercentage = getPersonalTodoPercentage(),
//                personalProgressPercentage = getPersonalProgressPercentage(),
//                personalDonePercentage = getPersonalDonePercentage(),
//                teamDonePercentage = getTeamDonePercentage(),
//                personalTodos = personalTodos,
//                teamTodo = teamTodos
//            )
        homeList.add(2,tag)
        homeView?.initHomeList(homeList)

//        } else {
//            personalTodos = allTodos.filter { it.assignee == null }
//            teamTodos = allTodos.filter { it.assignee != null }
//        }
    }

    override fun onSuccessPersonalTodo(todos: List<TodoItem>?) {
//        if (todos != null) {
//            personalTodosList.addAll(todos)
//        }
        val tag = DataItem.TagItem(Tag(1, "Recently Team", todos!!))
//        val header =
        homeList.add(1,tag)
        homeView?.initHomeList(homeList)
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