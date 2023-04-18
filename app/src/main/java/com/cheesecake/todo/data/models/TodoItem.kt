package com.cheesecake.todo.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoItem(
    val id: String,
    val title: String,
    val description: String,
    val assignee: String? = null,
    @SerializedName("status") val status: TodoState,
    val creationTime: String,
): Parcelable

