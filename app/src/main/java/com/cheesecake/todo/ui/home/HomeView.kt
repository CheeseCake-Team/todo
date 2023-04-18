package com.cheesecake.todo.ui.home

interface HomeView {
    fun initHomeList(homeList: MutableList<DataItem>)

    fun showError(message: String)

}