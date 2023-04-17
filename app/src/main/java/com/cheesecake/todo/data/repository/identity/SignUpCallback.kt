package com.cheesecake.todo.data.repository.identity

interface SignUpCallback {

    fun onSignUpComplete()

    fun onSignUpFail(error: String)
}