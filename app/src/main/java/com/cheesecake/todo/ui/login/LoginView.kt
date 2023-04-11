package com.cheesecake.todo.ui.login

interface LoginView {
    fun navigateToHomeScreen(username:String)
    fun showError(error: String)

}
