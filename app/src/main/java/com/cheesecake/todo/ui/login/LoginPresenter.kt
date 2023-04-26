package com.cheesecake.todo.ui.login

import android.util.Log
import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.models.response.LoginValue
import com.cheesecake.todo.data.repository.identity.IdentityRepository
import com.cheesecake.todo.data.repository.identity.LoginCallback
import com.cheesecake.todo.ui.base.BasePresenter
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable

class LoginPresenter(
    private val identityRepository: IdentityRepository, private val loginView: LoginView
) : BasePresenter<IdentityRepository, LoginView>(identityRepository, loginView) {

    fun login(username: String, password: String) {
        val observer = object : SingleObserver<BaseResponse<LoginValue>> {
            override fun onSubscribe(d: Disposable) {
                //show loading screen
                Log.d("TAG", "onSubscribe: ")
            }

            override fun onError(e: Throwable) {
                loginView.showError(e.toString())
            }

            override fun onSuccess(t: BaseResponse<LoginValue>) {

                if (t.isSuccess) {
                    //disable loading screen

                    identityRepository.saveTokenAndExpireDate(t.value.token, t.value.expireAt)
                    loginView.navigateToHomeScreen()

                } else loginView.showError(t.message)
            }
        }

        identityRepository.login(username, password, observer)
    }
}
