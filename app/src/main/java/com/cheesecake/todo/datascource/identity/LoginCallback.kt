package com.cheesecake.todo.datascource.identity

interface LoginCallback {
    fun onLoginSuccess(pair: Pair<String,String>)
    fun onLoginError(error: String)
}