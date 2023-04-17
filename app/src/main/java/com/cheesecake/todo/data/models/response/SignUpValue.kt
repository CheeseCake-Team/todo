package com.cheesecake.todo.data.models.response

import java.io.Serializable

data class SignUpValue(
    val userId: String,
    val username: String
) : Serializable

data class SignUpResponse(
    override val value: SignUpValue?,
    override val message: String?,
    override val isSuccess: Boolean
) : BaseResponse<SignUpValue>(value, message, isSuccess)