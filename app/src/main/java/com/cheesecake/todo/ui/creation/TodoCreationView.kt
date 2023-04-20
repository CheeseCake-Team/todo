package com.cheesecake.todo.ui.creation

interface TodoCreationView {
    fun showError(message: String)
    fun navigateToLoginScreen()
    fun showDialog()
}