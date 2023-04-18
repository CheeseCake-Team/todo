package com.cheesecake.todo.data.models

import com.google.gson.annotations.SerializedName

enum class TodoState(val value: Int) {
    @SerializedName("0")
    TODO(0),
    @SerializedName("1")
    IN_PROGRESS(1),
    @SerializedName("2")
    DONE(2)
}