package com.cheesecake.todo.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoItem(
    val id: String,
    val title: String,
    val description: String,
    val assignee: String? = null,
    val status: Int,
    val creationTime: String,
): Parcelable

