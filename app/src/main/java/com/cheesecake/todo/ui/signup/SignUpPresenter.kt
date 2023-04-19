package com.cheesecake.todo.ui.signup


import com.cheesecake.todo.BuildConfig
import com.cheesecake.todo.data.repository.identity.IdentityRepository
import com.cheesecake.todo.data.repository.identity.SignUpCallback
import com.cheesecake.todo.ui.base.BasePresenter

class SignUpPresenter(
    private val identityRepository: IdentityRepository, private val signUpView: SignUpView
) : BasePresenter<IdentityRepository, SignUpView>(identityRepository, signUpView), SignUpCallback {

    override fun onSignUpSuccess() {
        signUpView.navigateToLoginScreen()
    }

    override fun onSignUpFail(error: String) {
        signUpView.showError(error)
    }

    fun signUp(
        username: String, password: String, confirmationPassword: String
    ) {
        when {
            !isUsernameValid(username) -> signUpView.showError("Invalid username!")
            !isPasswordValid(password) -> signUpView.showError("Invalid password!")
            !arePasswordsTheSame(
                password, confirmationPassword
            ) -> signUpView.showError("Passwords are not matched!")
            else -> identityRepository.signUp(username, password, BuildConfig.teamId, this)
        }
    }

    private fun isUsernameValid(username: String) = username.length > 3 && username.isNotBlank()

    private fun isPasswordValid(password: String) = password.length > 8

    private fun arePasswordsTheSame(password: String, confirmationPassword: String) =
        password == confirmationPassword

}