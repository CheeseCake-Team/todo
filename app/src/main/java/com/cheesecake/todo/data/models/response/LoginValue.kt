package com.cheesecake.todo.data.models.response


data class LoginValue(
    val token: String,
    val expireAt: String
)

data class LoginResponse(
    override val value: LoginValue?,
    override val message: String?,
    override val isSuccess: Boolean
) : BaseResponse<LoginValue>(value, message, isSuccess)

