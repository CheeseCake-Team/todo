package com.cheesecake.todo.data.network.identity

import com.cheesecake.todo.data.network.ResponseCallback

interface IdentityNetworkService {
    fun login(username: String, password: String, responseCallback: ResponseCallback)

    fun signUp(
        username: String, password: String, teamId: String, responseCallback: ResponseCallback
    )
}