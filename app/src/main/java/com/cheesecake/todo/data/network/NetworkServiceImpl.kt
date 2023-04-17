package com.cheesecake.todo.data.network

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.request.TodoPersonalRequest
import com.cheesecake.todo.data.models.request.TodoStatus
import com.cheesecake.todo.data.models.request.TodoTeamRequest
import com.cheesecake.todo.utils.Constants.AUTHORIZATION_HEADER
import com.cheesecake.todo.utils.Constants.LOGIN_ENDPOINT
import com.cheesecake.todo.utils.Constants.PERSONAL_ENDPOINT
import com.cheesecake.todo.utils.Constants.SIGNUP_ENDPOINT
import com.cheesecake.todo.utils.Constants.TEAM_ENDPOINT
import com.cheesecake.todo.utils.makeCall
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.Request


class NetworkServiceImpl(private val okHttpClient: OkHttpClient) : NetworkService {

    override fun getPersonalTodos(callback: (List<TodoItem>?, String?) -> Unit) {
        val request = Request.Builder()
            .url(PERSONAL_ENDPOINT)
            .build()

        okHttpClient.makeCall(request) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> {
                    callback(null, apiResult.errorMessage)
                }
                is ApiResult.Success<*> -> {
                    val todos = parseTodos(apiResult.responseBody)
                    callback(todos, null)
                }
            }
        }
    }

    override fun getTeamTodos(callback: (List<TodoItem>?, String?) -> Unit) {
        val request = Request.Builder()
            .url(TEAM_ENDPOINT)
            .build()

        okHttpClient.makeCall(request) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> {
                    callback(null, apiResult.errorMessage)
                }
                is ApiResult.Success<*> -> {
                    val todos = parseTodos(apiResult.responseBody)
                    callback(todos, null)
                }
            }
        }
    }

    override fun createPersonalTodo(
        todoPersonalRequest: TodoPersonalRequest, callback: (String?) -> Unit
    ) {
        val requestBody = createMultipartBody(
            "title" to todoPersonalRequest.title, "description" to todoPersonalRequest.description
        )
        val request = Request.Builder()
            .url(PERSONAL_ENDPOINT)
            .post(requestBody)
            .build()

        okHttpClient.makeCall(request) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> {
                    callback(apiResult.errorMessage)
                }
                is ApiResult.Success<*> -> {
                    callback(null)
                }
            }
        }
    }

    override fun createTeamTodo(
        todoPersonalRequest: TodoTeamRequest,
        callback: (String?) -> Unit
    ) {
        val requestBody = createMultipartBody(
            "title" to todoPersonalRequest.title,
            "description" to todoPersonalRequest.description,
            "assignee" to todoPersonalRequest.assignee
        )

        val request = Request.Builder()
            .url(TEAM_ENDPOINT)
            .post(requestBody)
            .build()

        okHttpClient.makeCall(request) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> {
                    callback(apiResult.errorMessage)
                }
                is ApiResult.Success<*> -> {
                    callback(null)
                }
            }
        }
    }

    override fun changePersonalTodoStatus(
        todoStatus: TodoStatus,
        callback: (String?) -> Unit
    ) {
        val requestBody = createMultipartBody(
            "id" to todoStatus.todoId, "status" to todoStatus.newStatus.value.toString()
        )

        val request = Request.Builder()
            .url(PERSONAL_ENDPOINT)
            .put(requestBody)
            .build()

        okHttpClient.makeCall(request) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> {
                    callback(apiResult.errorMessage)
                }
                is ApiResult.Success<*> -> {
                    callback(null)
                }
            }
        }
    }

    override fun changeTeamTodoStatus(
        todoStatus: TodoStatus,
        callback: (String?) -> Unit
    ) {
        val requestBody = createMultipartBody(
            "id" to todoStatus.todoId, "status" to todoStatus.newStatus.value.toString()
        )

        val request = Request.Builder()
            .url(TEAM_ENDPOINT)
            .put(requestBody)
            .build()

        okHttpClient.makeCall(request) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> {
                    callback(apiResult.errorMessage)
                }
                is ApiResult.Success<*> -> {
                    callback(null)
                }
            }
        }
    }

    override fun login(
        username: String, password: String,
        callback: (Pair<String, String>?, String?) -> Unit
    ) {
        val credentials = Credentials.basic(username, password)

        val request = Request.Builder()
            .url(LOGIN_ENDPOINT)
            .addHeader(AUTHORIZATION_HEADER, credentials)
            .build()

        okHttpClient.makeCall(request) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> {
                    callback(null, apiResult.errorMessage)
                }
                is ApiResult.Success<*> -> {
                    val token = parseLoginResponse(apiResult.responseBody)
                    callback(token, null)
                }
            }
        }
    }

    override fun signUp(
        username: String, password: String, teamId: String,
        signUpCallback: (String?) -> Unit
    ) {
        val requestBody = createMultipartBody(
            "username" to username, "password" to password, "teamId" to teamId
        )

        val request = Request.Builder()
            .url(SIGNUP_ENDPOINT)
            .post(requestBody)
            .build()

        okHttpClient.makeCall(request) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> {
                    signUpCallback(apiResult.errorMessage)
                }
                is ApiResult.Success<*> -> {
                    signUpCallback(null)
                }
            }
        }
    }
}
