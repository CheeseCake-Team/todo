package com.cheesecake.todo.data.repository.identity

interface LoginCallback {
    fun onLoginSuccess(pair: Pair<String,String>)
    fun onLoginError(error: String)
}