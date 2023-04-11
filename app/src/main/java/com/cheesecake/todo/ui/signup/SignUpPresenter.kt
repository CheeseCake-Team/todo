package com.cheesecake.todo.ui.signup

import com.cheesecake.todo.BuildConfig
import com.cheesecake.todo.data.network.NetworkService
import com.cheesecake.todo.data.repository.identity.AuthCallback
import com.cheesecake.todo.data.repository.identity.AuthRepository
import com.cheesecake.todo.utils.arePasswordsTheSame
import com.cheesecake.todo.utils.isPasswordValid
import com.cheesecake.todo.utils.isUsernameValid

class SignUpPresenter(
    private val networkService: NetworkService,
) {

    private var signUpView: SignUpView? = null
    private val callback = object : AuthCallback {

        override fun onSuccess(pair: Pair<String, String>,username: String?) {
            signUpView?.navigateToLoginScreen()
        }

        override fun onError(error: String) {
            signUpView?.showError("Sign up failed!")
        }
    }

    fun signUp(
        username: String,
        password: String,
        confirmationPassword: String
    ) {
        when {
            !isUsernameValid(username) ->
                signUpView?.showError("Invalid username!")
            !isPasswordValid(password) ->
                signUpView?.showError("Invalid password!")
            !arePasswordsTheSame(password, confirmationPassword) ->
                signUpView?.showError("Passwords are not matched!")
            else -> signUpDone(username, password, BuildConfig.teamId, callback)
        }
    }

    fun attachView(view: SignUpView) {
        signUpView = view
    }

    fun detachView() {
        signUpView = null
    }

    private fun signUpDone(
        username: String,
        password: String,
        teamId: String,
        callback: AuthCallback,
    ) {
        networkService.signUp(username, password, teamId) { pair, error ->
            if (error != null) {
                callback.onError(error)
            } else {
                callback.onSuccess(pair!!)
            }
        }
    }

}