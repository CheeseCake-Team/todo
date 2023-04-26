package com.cheesecake.todo.data.repository.identity

import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.models.response.LoginValue
import com.cheesecake.todo.data.models.response.SignUpValue
import com.cheesecake.todo.data.network.identity.IdentityNetworkService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers


class IdentityRepositoryImpl(
    private val identityNetworkService: IdentityNetworkService,
    private val sharedPreferencesService: SharedPreferencesService
) : IdentityRepository {

    override fun login(
        username: String,
        password: String,
        observer: SingleObserver<BaseResponse<LoginValue>>
    ) {

        identityNetworkService.login(username, password)
            .subscribe(observer)
    }

    override fun signUp(
        username: String,
        password: String,
        teamId: String,
        observer: SingleObserver<BaseResponse<SignUpValue>>
    ) {
        identityNetworkService.signUp(username, password, teamId)
            .subscribe(observer)
    }

    override fun saveTokenAndExpireDate(token: String, expireDate: String) {
        sharedPreferencesService.saveTokenAndExpireDate(token, expireDate)
    }
}
