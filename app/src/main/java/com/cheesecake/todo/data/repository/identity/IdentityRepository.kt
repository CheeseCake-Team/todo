package com.cheesecake.todo.data.repository.identity

import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.models.response.LoginValue
import com.cheesecake.todo.data.models.response.SignUpValue
import com.cheesecake.todo.data.repository.BaseRepository

interface IdentityRepository {
    fun login(
        username: String,
        password: String,
        onSuccess: (BaseResponse<LoginValue>) -> Unit,
        onError: (e: Throwable) -> Unit,
    )

    fun signUp(
        username: String,
        password: String,
        teamId: String,
        onSuccess: (BaseResponse<SignUpValue>) -> Unit,
        onError: (e: Throwable) -> Unit,
    )

    fun saveTokenAndExpireDate(token: String, expireDate: String)

}