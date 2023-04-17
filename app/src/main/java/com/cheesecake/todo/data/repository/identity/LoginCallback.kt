package com.cheesecake.todo.data.repository.identity

import com.cheesecake.todo.data.models.response.LoginValue

interface LoginCallback {
    fun onLoginComplete(loginValue: LoginValue, username: String? = null)
    fun onLoginFail(error: String)
}