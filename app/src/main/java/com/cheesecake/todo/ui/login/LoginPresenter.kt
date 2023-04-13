package com.cheesecake.todo.ui.login

import com.cheesecake.todo.data.repository.identity.LoginCallback
import com.cheesecake.todo.data.repository.identity.AuthRepository

class LoginPresenter(private val authRepository: AuthRepository) : LoginCallback {
    private var loginView: LoginView? = null
    fun attachView(view: LoginView) {
        loginView = view
    }

    fun detachView() {
        loginView = null
    }

    fun login(username: String, password: String) {
        authRepository.login(username, password, this)
    }

    override fun onLoginComplete(token: String, expirationDate: String, username: String?) {
        authRepository.saveTokenAndExpireDate(token, expirationDate)
        loginView?.navigateToHomeScreen(username!!)
    }

    override fun onLoginFail(error: String) {
        loginView?.showError(error)
    }

}
