package com.cheesecake.todo.data.repository.identity

interface AuthCallback {
    fun onSuccess(pair: Pair<String, String>, username: String? = null)
    fun onError(error: String)
}