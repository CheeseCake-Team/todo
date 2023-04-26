package com.cheesecake.todo.data.repository.identity

import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.models.response.LoginValue
import com.cheesecake.todo.data.network.identity.IdentityNetworkService
import com.cheesecake.todo.data.network.ResponseCallback
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class IdentityRepositoryImpl(
    private val identityNetworkService: IdentityNetworkService,
    private val sharedPreferencesService: SharedPreferencesService
) : IdentityRepository {

    override fun login(username: String, password: String, loginCallback: LoginCallback) {
        identityNetworkService.login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.isSuccess) {
                    loginCallback.onLoginSuccess(response.value)
                } else {
                    loginCallback.onLoginFail(response.message)
                }
            }, { error ->
                loginCallback.onLoginFail(error.message ?: "Unknown error")
            })
    }

    override fun signUp(
        username: String,
        password: String,
        teamId: String,
        signUpCallback: SignUpCallback
    ) {
        identityNetworkService.signUp(username, password, teamId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.isSuccess) {
                    signUpCallback.onSignUpSuccess()
                } else {
                    signUpCallback.onSignUpFail(response.message)
                }
            }, { error ->
                signUpCallback.onSignUpFail(error.message ?: "Unknown error")
            })
    }

    override fun saveTokenAndExpireDate(token: String, expireDate: String) {
        sharedPreferencesService.saveTokenAndExpireDate(token, expireDate)
    }
}
