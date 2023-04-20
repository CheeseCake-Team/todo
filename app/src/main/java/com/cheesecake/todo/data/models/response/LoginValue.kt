package com.cheesecake.todo.data.models.response

import com.google.gson.annotations.SerializedName

data class LoginValue(
    @SerializedName("token") val token: String,
    @SerializedName("expireAt") val expireAt: String
)

