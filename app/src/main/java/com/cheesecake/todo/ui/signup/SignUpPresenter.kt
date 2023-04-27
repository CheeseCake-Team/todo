package com.cheesecake.todo.ui.signup


import com.cheesecake.todo.BuildConfig
import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.models.response.SignUpValue
import com.cheesecake.todo.data.repository.identity.IdentityRepository
import com.cheesecake.todo.data.repository.identity.IdentityRepositoryImpl
import com.cheesecake.todo.ui.base.BasePresenter

class SignUpPresenter(
    private val identityRepository: IdentityRepositoryImpl, private val signUpView: SignUpView
) : BasePresenter<SignUpView>(identityRepository, signUpView) {

    fun signUp(
        username: String, password: String, confirmationPassword: String
    ) {
        when {
            !isUsernameValid(username) -> signUpView.showError("Invalid username!")
            !isPasswordValid(password) -> signUpView.showError("Invalid password!")
            !arePasswordsTheSame(
                password, confirmationPassword
            ) -> signUpView.showError("Passwords are not matched!")
            else -> {

                identityRepository.signUp(
                    username, password, BuildConfig.teamId, ::onSuccess, ::onError
                )
            }
        }
    }

    private fun onSuccess(t: BaseResponse<SignUpValue>) {
        if (t.isSuccess) signUpView.navigateToLoginScreen()
        else signUpView.showError(t.message)

    }

    private fun onError(e: Throwable) {
        signUpView.showError(e.toString())
    }

    private fun isUsernameValid(username: String) = username.length > 3 && username.isNotBlank()

    private fun isPasswordValid(password: String) = password.length > 8

    private fun arePasswordsTheSame(password: String, confirmationPassword: String) =
        password == confirmationPassword

}