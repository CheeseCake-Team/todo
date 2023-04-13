package com.cheesecake.todo.data.repository.identity

import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.network.NetworkService


class AuthRepositoryImpl(
    private val networkService: NetworkService,
    private val sharedPreferencesService: SharedPreferencesService
) : AuthRepository {

    override fun login(username: String, password: String, callback: LoginCallback) {

        networkService.login(username, password) { pair, error ->
            if (error != null) {
                callback.onLoginFail(error)
            } else {
                callback.onLoginComplete(pair!!, username)
            }
        }
    }

    override fun signUp(
        username: String,
        password: String,
        teamId: String,
        callback: LoginCallback
    ) {
        networkService.signUp(username, password, teamId) { pair, error ->
            if (error != null) {
                callback.onLoginFail(error)
            } else {
                callback.onLoginComplete(pair!!)
            }
        }
    }

    override fun saveTokenAndExpireDate(token: String, expireDate: String) {
        sharedPreferencesService.saveToken(token)
        sharedPreferencesService.saveExpireDate(expireDate)
    }
}
