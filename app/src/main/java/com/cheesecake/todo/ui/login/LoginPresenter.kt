package com.cheesecake.todo.ui.login

import android.util.Log
import com.cheesecake.todo.data.models.response.LoginValue
import com.cheesecake.todo.data.repository.identity.IdentityRepository
import com.cheesecake.todo.data.repository.identity.LoginCallback
import com.cheesecake.todo.ui.base.BasePresenter

class LoginPresenter(
    private val identityRepository: IdentityRepository, private val loginView: LoginView
) : BasePresenter<IdentityRepository, LoginView>(identityRepository, loginView), LoginCallback {

    fun login(username: String, password: String) {
        identityRepository.login(username, password, this)
    }

    override fun onLoginSuccess(loginValue: LoginValue) {
        identityRepository.saveTokenAndExpireDate(loginValue.token, loginValue.expireAt)
        Log.d("TAG", "onLoginComplete:${loginValue.token}  ")
        loginView.navigateToHomeScreen()
    }

    override fun onLoginFail(error: String) {
        loginView.showError(error)
    }

}
