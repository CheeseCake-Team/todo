package com.cheesecake.todo.data.models.response

import com.cheesecake.todo.data.models.TodoItem

data class TodoTeamResponse(
    override val value: List<TodoItem>,
    override val message: String?,
    override val isSuccess: Boolean
) : BaseResponse<List<TodoItem>>(value, message, isSuccess)
