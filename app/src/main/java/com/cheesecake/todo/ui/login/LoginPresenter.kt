package com.cheesecake.todo.ui.login

import android.util.Log
import com.cheesecake.todo.data.models.response.LoginValue
import com.cheesecake.todo.data.repository.identity.IdentityRepository
import com.cheesecake.todo.data.repository.identity.LoginCallback

class LoginPresenter(private val identityRepository: IdentityRepository) : LoginCallback {
    private var loginView: LoginView? = null
    fun attachView(view: LoginView) {
        loginView = view
    }

    fun detachView() {
        loginView = null
    }

    fun login(username: String, password: String) {
        identityRepository.login(username, password, this)
    }

    override fun onLoginSuccess(loginValue: LoginValue) {
        identityRepository.saveTokenAndExpireDate(loginValue.token, loginValue.expireAt)
        Log.d("TAG", "onLoginComplete:${loginValue.token}  ")
        loginView?.navigateToHomeScreen()
    }

    override fun onLoginFail(error: String) {
        loginView?.showError(error)
    }

}
