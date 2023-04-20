package com.cheesecake.todo.data.network.todos

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.request.TodoPersonalRequest
import com.cheesecake.todo.data.models.request.TodoStatusRequest
import com.cheesecake.todo.data.models.request.TodoTeamRequest
import com.cheesecake.todo.data.network.ResponseCallback
import com.cheesecake.todo.utils.makeCall
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class TodoNetworkServiceImpl(private val okHttpClient: OkHttpClient) : TodoNetworkService {
    override fun getPersonalTodos(responseCallback: ResponseCallback) {
        val request = Request.Builder().url(PERSONAL_ENDPOINT).build()
        okHttpClient.makeCall<List<TodoItem>>(request, responseCallback)
    }

    override fun getTeamTodos(responseCallback: ResponseCallback) {
        val request = Request.Builder().url(TEAM_ENDPOINT).build()
        okHttpClient.makeCall<List<TodoItem>>(request, responseCallback)
    }

    override fun createPersonalTodo(
        todoPersonalRequest: TodoPersonalRequest, responseCallback: ResponseCallback
    ) {

        val requestBody = FormBody.Builder()
            .add("title", todoPersonalRequest.title)
            .add("description", todoPersonalRequest.description)
            .build()
        val request = Request.Builder().url(PERSONAL_ENDPOINT).post(requestBody).build()

        okHttpClient.makeCall<TodoItem>(request, responseCallback)
    }

    override fun createTeamTodo(
        todoPersonalRequest: TodoTeamRequest, responseCallback: ResponseCallback
    ) {
        val requestBody = FormBody.Builder()
            .add("title", todoPersonalRequest.title)
            .add("description", todoPersonalRequest.description)
            .add("assignee", todoPersonalRequest.assignee)
            .build()

        val request = Request.Builder().url(TEAM_ENDPOINT).post(requestBody).build()

        okHttpClient.makeCall<TodoItem>(request, responseCallback)
    }

    override fun updatePersonalTodoStatus(
        todoStatusRequest: TodoStatusRequest, responseCallback: ResponseCallback
    ) {

        val requestBody = FormBody.Builder()
            .add("id", todoStatusRequest.todoId)
            .add("status", todoStatusRequest.newStatus.value.toString())
            .build()

        val request = Request.Builder().url(PERSONAL_ENDPOINT).put(requestBody).build()
        okHttpClient.makeCall<String>(request, responseCallback)
    }

    override fun updateTeamTodoStatus(
        todoStatusRequest: TodoStatusRequest, responseCallback: ResponseCallback
    ) {
        val requestBody = FormBody.Builder()
            .add("id", todoStatusRequest.todoId)
            .add("status", todoStatusRequest.newStatus.value.toString())
            .build()

        val request = Request.Builder().url(TEAM_ENDPOINT).put(requestBody).build()
        okHttpClient.makeCall<String>(request, responseCallback)
    }

    private companion object {
        const val PERSONAL_ENDPOINT = "https://team-todo-62dmq.ondigitalocean.app/todo/personal"
        const val TEAM_ENDPOINT = "https://team-todo-62dmq.ondigitalocean.app/todo/team"
    }
}
