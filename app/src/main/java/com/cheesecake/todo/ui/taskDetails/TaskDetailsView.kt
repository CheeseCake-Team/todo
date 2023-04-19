package com.cheesecake.todo.ui.taskDetails

interface TaskDetailsView {

    fun updateState(position: Int)

    fun attachView(taskDetailsView: TaskDetailsView)

    fun detachView()
}