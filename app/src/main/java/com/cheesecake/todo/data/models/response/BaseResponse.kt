package com.cheesecake.todo.data.models.response

open class BaseResponse<T>(
    open val value: T?,
    open val message: String?,
    open val isSuccess: Boolean
)
