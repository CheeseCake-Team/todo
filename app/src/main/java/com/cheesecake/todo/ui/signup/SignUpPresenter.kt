package com.cheesecake.todo.ui.signup


import android.util.Log
import com.cheesecake.todo.BuildConfig
import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.models.response.SignUpValue
import com.cheesecake.todo.data.repository.identity.IdentityRepository
import com.cheesecake.todo.ui.base.BasePresenter
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable

class SignUpPresenter(
    private val identityRepository: IdentityRepository, private val signUpView: SignUpView
) : BasePresenter<IdentityRepository, SignUpView>(identityRepository, signUpView) {

    fun signUp(
        username: String, password: String, confirmationPassword: String
    ) {

        val observer = object : SingleObserver<BaseResponse<SignUpValue>> {
            override fun onSubscribe(d: Disposable) {
                //show loading screen
                Log.d("TAG", "onSubscribe: ")
            }

            override fun onSuccess(t: BaseResponse<SignUpValue>) {
                if (t.isSuccess) signUpView.navigateToLoginScreen()
                else signUpView.showError(t.message)

            }

            override fun onError(e: Throwable) {
                signUpView.showError(e.toString())
            }


        }


        when {
            !isUsernameValid(username) -> signUpView.showError("Invalid username!")
            !isPasswordValid(password) -> signUpView.showError("Invalid password!")
            !arePasswordsTheSame(
                password, confirmationPassword
            ) -> signUpView.showError("Passwords are not matched!")
            else -> {

                identityRepository.signUp(username, password, BuildConfig.teamId, observer)
            }
        }
    }

    private fun isUsernameValid(username: String) = username.length > 3 && username.isNotBlank()

    private fun isPasswordValid(password: String) = password.length > 8

    private fun arePasswordsTheSame(password: String, confirmationPassword: String) =
        password == confirmationPassword

}