package com.cheesecake.todo.data.repository.identity

import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.models.response.LoginValue
import com.cheesecake.todo.data.models.response.SignUpValue
import com.cheesecake.todo.data.repository.BaseRepository
import io.reactivex.rxjava3.core.SingleObserver

interface IdentityRepository : BaseRepository {
    fun login(
        username: String,
        password: String,
        observer: SingleObserver<BaseResponse<LoginValue>>
    )

    fun signUp(
        username: String,
        password: String,
        teamId: String,
        observer: SingleObserver<BaseResponse<SignUpValue>>
    )

    fun saveTokenAndExpireDate(token: String, expireDate: String)

}