package com.cheesecake.todo.data.models.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SignUpValue(
    val userId: String,
    val username: String
) : Serializable

data class SignUpResponse(
    val value: SignUpValue?,
    val message: String?,
    @SerializedName("isSuccess")
    val isSuccess: Boolean
)