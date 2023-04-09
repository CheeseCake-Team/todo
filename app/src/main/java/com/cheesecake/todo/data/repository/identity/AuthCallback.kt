package com.cheesecake.todo.data.repository.identity

interface AuthCallback {
    fun onSuccess(pair: Pair<String,String>)
    fun onError(error: String)
}