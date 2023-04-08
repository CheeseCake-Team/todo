package com.cheesecake.todo.ui.login

import android.util.Patterns
import com.cheesecake.todo.R
import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.repository.identity.AuthRepository
import com.cheesecake.todo.data.repository.identity.LoginCallback
import com.cheesecake.todo.utils.Constants.EXPIRY
import com.cheesecake.todo.utils.Constants.TOKEN

class LoginPresenter(
    private val authRepository: AuthRepository,
    private val preferencesService: SharedPreferencesService
) {

    private var loginView: LoginView? = null
    private val callback = object : LoginCallback {

        override fun onLoginSuccess(pair: Pair<String, String>) {
            loginView?.navigateToHomeScreen(pair)
            preferencesService.saveToken(TOKEN, pair.first)
            preferencesService.saveExpireDate(EXPIRY, pair.second)
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
        // call isUserNameValid and isPasswordValid
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
