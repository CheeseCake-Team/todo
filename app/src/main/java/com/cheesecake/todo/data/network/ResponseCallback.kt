package com.cheesecake.todo.data.network

import com.cheesecake.todo.data.models.response.BaseResponse

interface ResponseCallback {
    fun <T> onSuccess(response: BaseResponse<T>)
    fun onFail(error: String)
}