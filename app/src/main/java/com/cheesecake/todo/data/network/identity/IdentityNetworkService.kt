package com.cheesecake.todo.data.network.identity

import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.models.response.LoginValue
import com.cheesecake.todo.data.models.response.SignUpValue
import io.reactivex.rxjava3.core.Single

interface IdentityNetworkService {
    fun login(username: String, password: String): Single<BaseResponse<LoginValue>>

    fun signUp(
        username: String,
        password: String,
        teamId: String
    ): Single<BaseResponse<SignUpValue>>
}