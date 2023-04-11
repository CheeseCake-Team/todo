package com.cheesecake.todo.data.repository.identity

import com.cheesecake.todo.data.network.NetworkService


class AuthRepositoryImpl(private val networkService: NetworkService) : AuthRepository {

    override fun login(username: String, password: String, callback: AuthCallback) {

        networkService.login(username, password) { pair, error ->
            if (error != null) {
                callback.onError(error)
            } else {
                callback.onSuccess(pair!!, username)
            }
        }
    }


}
