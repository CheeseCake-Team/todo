package com.cheesecake.todo.ui.login

import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.repository.identity.AuthRepository
import com.cheesecake.todo.data.repository.identity.LoginCallback

class LoginPresenter(
    private val authRepository: AuthRepository,
    private val preferencesService: SharedPreferencesService
) {

    private var loginView: LoginView? = null
    private val callback = object : LoginCallback {

        override fun onLoginSuccess(pair: Pair<String, String>) {
            preferencesService.saveToken(pair.first)
            preferencesService.saveExpireDate(pair.second)
            loginView?.navigateToHomeScreen()
        }

        override fun onLoginError(error: String) {
            loginView?.showError(error)
        }
    }

    fun attachView(view: LoginView) {
        loginView = view
    }

    fun detachView() {
        loginView = null
    }

    fun login(username: String, password: String) {
        authRepository.login(username, password, callback)
    }

}
