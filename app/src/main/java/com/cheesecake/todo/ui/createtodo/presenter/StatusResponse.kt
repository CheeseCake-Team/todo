package com.cheesecake.todo.ui.createtodo.presenter

import com.cheesecake.todo.ui.createtodo.model.CreateToDoModel


interface StatusResponse {
    fun onSuccess(toDoModel: CreateToDoModel)
    fun onError(error: String)
}