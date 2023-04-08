package com.cheesecake.todo.ui.login

import android.util.Patterns
import com.cheesecake.todo.R
import com.cheesecake.todo.datascource.identity.LoginCallback
import com.cheesecake.todo.datascource.identity.AuthRepository

class LoginPresenter(private val authRepository: AuthRepository) {

    private var loginView: LoginView? = null
    private val callback = object : LoginCallback {

        override fun onLoginSuccess(pair: Pair<String, String>) {
            loginView?.navigateToHomeScreen(pair)
        }

        override fun onLoginError(error: String) {
            loginView?.showError(R.string.login_failed)
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

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 8
    }
}
