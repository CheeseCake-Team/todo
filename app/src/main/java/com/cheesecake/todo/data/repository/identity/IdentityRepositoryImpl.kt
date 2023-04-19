package com.cheesecake.todo.data.repository.identity

import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.models.response.LoginValue
import com.cheesecake.todo.data.network.identity.IdentityNetworkService
import com.cheesecake.todo.data.network.ResponseCallback


class IdentityRepositoryImpl(
    private val identityNetworkService: IdentityNetworkService,
    private val sharedPreferencesService: SharedPreferencesService
) : IdentityRepository {

    override fun login(username: String, password: String, loginCallback: LoginCallback) {
        identityNetworkService.login(username, password, object : ResponseCallback {
            override fun <T> onSuccess(response: BaseResponse<T>) {
                if (response.isSuccess) {
                    loginCallback.onLoginSuccess(response.value as LoginValue)
                } else
                    loginCallback.onLoginFail(response.message)
            }

            override fun onFail(error: String) {
                loginCallback.onLoginFail(error)
            }
        })
    }

    override fun signUp(
        username: String,
        password: String,
        teamId: String,
        signUpCallback: SignUpCallback
    ) {
        identityNetworkService.signUp(username, password, teamId, object : ResponseCallback {
            override fun <T> onSuccess(response: BaseResponse<T>) {
                if (response.isSuccess) {
                    signUpCallback.onSignUpSuccess()
                } else
                    signUpCallback.onSignUpFail(response.message)
            }

            override fun onFail(error: String) {
                signUpCallback.onSignUpFail(error)
            }
        })
    }

    override fun saveTokenAndExpireDate(token: String, expireDate: String) {
        sharedPreferencesService.saveTokenAndExpireDate(token, expireDate)
    }
}
