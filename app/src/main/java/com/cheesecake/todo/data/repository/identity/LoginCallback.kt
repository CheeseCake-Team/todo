package com.cheesecake.todo.data.repository.identity

interface LoginCallback {
    fun onLoginComplete(token: String, expirationDate: String, username: String? = null)
    fun onLoginFail(error: String)
}