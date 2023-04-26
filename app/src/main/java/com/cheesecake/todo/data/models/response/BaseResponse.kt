package com.cheesecake.todo.data.models.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("value") val value: T,
    @SerializedName("message") val message: String,
    @SerializedName("isSuccess") val isSuccess: Boolean,
)
