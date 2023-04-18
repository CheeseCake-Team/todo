package com.cheesecake.todo.ui.home

interface HomeView {
    fun initHomeList(homeList: MutableList<DataItem>)
    fun navigateToLoginScreen()

    fun showError(message: String)

}