package com.cheesecake.todo.ui.login

import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.repository.identity.AuthCallback
import com.cheesecake.todo.data.repository.identity.AuthRepository

class LoginPresenter(
    private val authRepository: AuthRepository,
    private val preferencesService: SharedPreferencesService
) {

    private var loginView: LoginView? = null
    private val callback = object : AuthCallback {

        override fun onSuccess(pair: Pair<String, String>, username: String?) {
            preferencesService.saveToken(pair.first)
            preferencesService.saveExpireDate(pair.second)
            loginView?.navigateToHomeScreen(username!!)
        }

        override fun onError(error: String) {
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
