package com.cheesecake.todo.datascource.identity

import com.cheesecake.todo.network.INetworkService


class AuthRepositoryImpl(private val networkService: INetworkService):AuthRepository {

    override fun login(username: String, password: String, callback: LoginCallback) {
        networkService.login(username, password) { pair, error ->
            if (error != null) {
                callback.onLoginError(error)
            } else {
                callback.onLoginSuccess(pair!!)
            }
        }
    }

    override fun signUp(username: String, password: String, teamId: String, callback: SignUpCallback) {
        networkService.signUp(username, password, teamId) { pair, error ->
            if (error != null) {
                callback.onSignUpError(error)
            } else {
                callback.onSignUpSuccess(pair!!)
            }
        }
    }
}
