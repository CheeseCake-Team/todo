package com.cheesecake.todo.datascource.identity

interface SignUpCallback {
    fun onSignUpSuccess(pair: Pair<String,String>)
    fun onSignUpError(error: String)
}