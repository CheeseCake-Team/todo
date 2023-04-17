package com.cheesecake.todo.data.models.response

import com.google.gson.annotations.SerializedName

open class BaseResponse<T>(
    open val value: T?,
    open val message: String?,
    @SerializedName("isSuccess")
    open val isSuccess: Boolean
)
