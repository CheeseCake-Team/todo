package com.cheesecake.todo.ui.signup

interface SignUpView {

    fun navigateToLoginScreen()

    fun showError(error: String)
}