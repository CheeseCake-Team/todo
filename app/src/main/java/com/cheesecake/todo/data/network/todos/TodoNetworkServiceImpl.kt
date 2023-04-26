package com.cheesecake.todo.data.network.todos

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.request.TodoPersonalRequest
import com.cheesecake.todo.data.models.request.TodoStatusRequest
import com.cheesecake.todo.data.models.request.TodoTeamRequest
import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.models.response.LoginValue
import com.cheesecake.todo.utils.makeObservable
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.core.Single
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class TodoNetworkServiceImpl(private val okHttpClient: OkHttpClient) : TodoNetworkService {
    override fun getPersonalTodos(): Single<BaseResponse<List<TodoItem>>> {
        val request = Request.Builder().url(PERSONAL_ENDPOINT).build()

        val type = object : TypeToken<BaseResponse<List<TodoItem>>>() {}.type
        return okHttpClient.makeObservable(request, type)
    }

    override fun getTeamTodos(): Single<BaseResponse<List<TodoItem>>> {
        val request = Request.Builder().url(TEAM_ENDPOINT).build()

        val type = object : TypeToken<BaseResponse<List<TodoItem>>>() {}.type
        return okHttpClient.makeObservable(request, type)
    }

    override fun createPersonalTodo(todoPersonalRequest: TodoPersonalRequest): Single<BaseResponse<TodoItem>> {

        val requestBody = FormBody.Builder().add("title", todoPersonalRequest.title)
            .add("description", todoPersonalRequest.description).build()
        val request = Request.Builder().url(PERSONAL_ENDPOINT).post(requestBody).build()

        val type = object : TypeToken<BaseResponse<TodoItem>>() {}.type
        return okHttpClient.makeObservable(request, type)
    }

    override fun createTeamTodo(todoPersonalRequest: TodoTeamRequest): Single<BaseResponse<TodoItem>> {
        val requestBody = FormBody.Builder().add("title", todoPersonalRequest.title)
            .add("description", todoPersonalRequest.description)
            .add("assignee", todoPersonalRequest.assignee).build()

        val request = Request.Builder().url(TEAM_ENDPOINT).post(requestBody).build()

        val type = object : TypeToken<BaseResponse<TodoItem>>() {}.type
        return okHttpClient.makeObservable(request, type)
    }

    override fun updatePersonalTodoStatus(todoStatusRequest: TodoStatusRequest): Single<BaseResponse<String>> {

        val requestBody = FormBody.Builder().add("id", todoStatusRequest.todoId)
            .add("status", todoStatusRequest.newStatus.value.toString()).build()

        val request = Request.Builder().url(PERSONAL_ENDPOINT).put(requestBody).build()

        val type = object : TypeToken<BaseResponse<String>>() {}.type
        return okHttpClient.makeObservable(request, type)
    }

    override fun updateTeamTodoStatus(todoStatusRequest: TodoStatusRequest): Single<BaseResponse<String>> {
        val requestBody = FormBody.Builder().add("id", todoStatusRequest.todoId)
            .add("status", todoStatusRequest.newStatus.value.toString()).build()

        val request = Request.Builder().url(TEAM_ENDPOINT).put(requestBody).build()

        val type = object : TypeToken<BaseResponse<String>>() {}.type
        return okHttpClient.makeObservable(request, type)
    }

    private companion object {
        const val PERSONAL_ENDPOINT = "https://team-todo-62dmq.ondigitalocean.app/todo/personal"
        const val TEAM_ENDPOINT = "https://team-todo-62dmq.ondigitalocean.app/todo/team"
    }
}
