package com.cheesecake.todo.ui.login

interface LoginView {

    fun navigateToHomeScreen(pair:Pair<String, String>)
    fun showError(error: Int)
}
