package com.cheesecake.todo.ui.login

import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.models.response.LoginValue
import com.cheesecake.todo.data.repository.identity.IdentityRepositoryImpl
import com.cheesecake.todo.ui.base.BasePresenter

class LoginPresenter(
    private val identityRepository: IdentityRepositoryImpl, private val loginView: LoginView
) : BasePresenter<LoginView>(identityRepository, loginView) {


    fun login(username: String, password: String) {
        identityRepository.login(username, password, ::onSuccess, ::onError)
    }

    private fun onError(e: Throwable) {
        loginView.showError(e.toString())
    }

    private fun onSuccess(response: BaseResponse<LoginValue>) {
        if (response.isSuccess) {
            //disable loading screen
            identityRepository.saveTokenAndExpireDate(response.value.token, response.value.expireAt)
            loginView.navigateToHomeScreen()

        } else loginView.showError(response.message)
    }
}
