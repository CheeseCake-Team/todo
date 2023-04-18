package com.cheesecake.todo.data.repository.identity

import com.cheesecake.todo.data.models.response.LoginValue

interface LoginCallback {
    fun onLoginComplete(loginValue: LoginValue)
    fun onLoginFail(error: String)
}