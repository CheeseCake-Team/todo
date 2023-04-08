package com.cheesecake.todo.data.repository.identity

interface SignUpCallback {
    fun onSignUpSuccess(pair: Pair<String,String>)
    fun onSignUpError(error: String)
}