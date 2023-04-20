package com.cheesecake.todo.ui.taskDetails

import com.cheesecake.todo.data.models.TodoState

interface TaskDetailsView {

    fun updateState(todoState: TodoState)

    fun showError(errorMessage: String)
}