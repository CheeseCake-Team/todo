package com.cheesecake.todo.data.network.todos

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.request.TodoPersonalRequest
import com.cheesecake.todo.data.models.request.TodoStatusRequest
import com.cheesecake.todo.data.models.request.TodoTeamRequest
import com.cheesecake.todo.data.models.response.BaseResponse
import io.reactivex.rxjava3.core.Single

interface TodoNetworkService {
    fun getPersonalTodos(): Single<BaseResponse<List<TodoItem>>>
    fun getTeamTodos(): Single<BaseResponse<List<TodoItem>>>

    fun createPersonalTodo(todoPersonalRequest: TodoPersonalRequest): Single<BaseResponse<TodoItem>>

    fun createTeamTodo(todoPersonalRequest: TodoTeamRequest): Single<BaseResponse<TodoItem>>

    fun updatePersonalTodoStatus(todoStatusRequest: TodoStatusRequest): Single<BaseResponse<String>>

    fun updateTeamTodoStatus(todoStatusRequest: TodoStatusRequest): Single<BaseResponse<String>>
}