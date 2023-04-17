package com.cheesecake.todo.data.repository.identity

import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.network.NetworkService


class IdentityRepositoryImpl(
    private val networkService: NetworkService,
    private val sharedPreferencesService: SharedPreferencesService
) : IdentityRepository {

    override fun login(username: String, password: String, loginCallback: LoginCallback) {
        networkService.login(username, password, loginCallback)
    }

    override fun signUp(
        username: String,
        password: String,
        teamId: String,
        signUpCallback: SignUpCallback
    ) {
        networkService.signUp(username, password, teamId, signUpCallback)
    }

    override fun saveTokenAndExpireDate(token: String, expireDate: String) {
        sharedPreferencesService.saveTokenAndExpireDate(token, expireDate)
    }
}
