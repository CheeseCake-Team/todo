package com.cheesecake.todo.data.repository.identity

import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.network.NetworkService


class AuthRepositoryImpl(
    private val networkService: NetworkService,
    private val sharedPreferencesService: SharedPreferencesService
) : AuthRepository {

    override fun login(username: String, password: String, loginCallback: LoginCallback) {

        networkService.login(username, password) { pair, error ->
            if (error != null) {
                loginCallback.onLoginFail(error)
            } else {
                val token = pair?.first
                val expirationDate = pair?.second
                if (token != null && expirationDate != null) {
                    loginCallback.onLoginComplete(token, expirationDate, username)
                }
            }
        }
    }

    override fun signUp(
        username: String,
        password: String,
        teamId: String,
        signUpCallback: SignUpCallback
    ) {
        networkService.signUp(username, password, teamId) { error ->
            if (error != null) {
                signUpCallback.onSignUpFail(error)
            } else {
                signUpCallback.onSignUpComplete()
            }
        }
    }

    override fun saveTokenAndExpireDate(token: String, expireDate: String) {
        sharedPreferencesService.saveToken(token)
        sharedPreferencesService.saveExpireDate(expireDate)
    }
}
