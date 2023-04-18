package com.cheesecake.todo.data.network

import com.cheesecake.todo.data.models.response.BaseResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MultipartBody

fun createMultipartBody(vararg fields: Pair<String, String?>) =
    MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .apply {
            fields.forEach { (key, value) ->
                if (value != null) {
                    addFormDataPart(key, value)
                }
            }
        }
        .build()

fun<T> parseResponse(response: String?): BaseResponse<T> = Gson().fromJson(response,
    object : TypeToken<BaseResponse<T>>() {}.type)