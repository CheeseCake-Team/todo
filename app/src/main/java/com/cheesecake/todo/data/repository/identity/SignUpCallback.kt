package com.cheesecake.todo.data.repository.identity

interface SignUpCallback {

    fun onSignUpSuccess()

    fun onSignUpFail(error: String)
}