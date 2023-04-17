package com.cheesecake.todo.ui.signup


import com.cheesecake.todo.data.repository.identity.IdentityRepository
import com.cheesecake.todo.data.repository.identity.SignUpCallback
import com.cheesecake.todo.utils.arePasswordsTheSame
import com.cheesecake.todo.utils.isPasswordValid
import com.cheesecake.todo.utils.isUsernameValid

class SignUpPresenter(private val identityRepository: IdentityRepository) : SignUpCallback {

    private var signUpView: SignUpView? = null

    override fun onSignUpComplete() {
        signUpView?.navigateToLoginScreen()
    }

    override fun onSignUpFail(error: String) {
        signUpView?.showError(error)
    }

    fun signUp(
        username: String, password: String, confirmationPassword: String
    ) {
        when {
            !isUsernameValid(username) -> signUpView?.showError("Invalid username!")
            !isPasswordValid(password) -> signUpView?.showError("Invalid password!")
            !arePasswordsTheSame(
                password,
                confirmationPassword
            ) -> signUpView?.showError("Passwords are not matched!")
            else -> identityRepository.signUp(username, password, "BuildConfig.teamId", this)
        }
    }

    fun attachView(view: SignUpView) {
        signUpView = view
    }

    fun detachView() {
        signUpView = null
    }

}