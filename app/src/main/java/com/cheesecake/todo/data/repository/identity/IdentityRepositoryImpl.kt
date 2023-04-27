package com.cheesecake.todo.data.repository.identity

import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.models.response.LoginValue
import com.cheesecake.todo.data.models.response.SignUpValue
import com.cheesecake.todo.data.network.identity.IdentityNetworkService
import com.cheesecake.todo.data.repository.BaseRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers


class IdentityRepositoryImpl(
    private val identityNetworkService: IdentityNetworkService,
    private val sharedPreferencesService: SharedPreferencesService
) : IdentityRepository, BaseRepository() {

    override fun login(
        username: String,
        password: String,
        onSuccess: (BaseResponse<LoginValue>) -> Unit,
        onError: (e: Throwable) -> Unit
    ) {
        identityNetworkService.login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = onSuccess, onError = onError)
            .addTo(compositeDisposable)

    }

    override fun signUp(
        username: String,
        password: String,
        teamId: String,
        onSuccess: (BaseResponse<SignUpValue>) -> Unit,
        onError: (e: Throwable) -> Unit
    ) {
        identityNetworkService.signUp(username, password, teamId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = onSuccess, onError = onError)
            .addTo(compositeDisposable)

    }

    override fun saveTokenAndExpireDate(token: String, expireDate: String) {
        sharedPreferencesService.saveTokenAndExpireDate(token, expireDate)
    }
}
