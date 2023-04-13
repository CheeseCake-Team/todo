package com.cheesecake.todo.ui.login

import com.cheesecake.todo.data.repository.identity.AuthCallback
import com.cheesecake.todo.data.repository.identity.AuthRepository

class LoginPresenter(private val authRepository: AuthRepository) : AuthCallback {
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

    override fun onSuccess(pair: Pair<String, String>, username: String?) {
        authRepository.saveTokenAndExpireDate(pair.first, pair.second)
        loginView?.navigateToHomeScreen(username!!)
    }

    override fun onError(error: String) {
        loginView?.showError(error)
    }

}
