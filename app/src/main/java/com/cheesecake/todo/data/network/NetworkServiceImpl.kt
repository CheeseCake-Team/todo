package com.cheesecake.todo.data.network

import com.cheesecake.todo.data.models.request.TodoPersonalRequest
import com.cheesecake.todo.data.models.request.TodoStatus
import com.cheesecake.todo.data.models.request.TodoTeamRequest
import com.cheesecake.todo.data.repository.identity.LoginCallback
import com.cheesecake.todo.data.repository.identity.SignUpCallback
import com.cheesecake.todo.data.repository.todos.TodoCallback
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

    override fun getPersonalTodos(todoCallback: TodoCallback) {
        val request = Request.Builder()
            .url(PERSONAL_ENDPOINT)
            .build()

        okHttpClient.makeCall(request) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> {
                    todoCallback.onError(apiResult.errorMessage)
                }
                is ApiResult.Success -> {
                    val todos = parsePersonalTodos(apiResult.responseBody)
                    if (todos.isSuccess)
                        todoCallback.onSuccess(todos.value)
                    else
                        todoCallback.onError(todos.message!!)
                }
            }
        }
    }

    override fun getTeamTodos(todoCallback: TodoCallback) {
        val request = Request.Builder()
            .url(TEAM_ENDPOINT)
            .build()

        okHttpClient.makeCall(request) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> {
                    todoCallback.onError(apiResult.errorMessage)
                }
                is ApiResult.Success -> {
                    val todos = parseTeamTodos(apiResult.responseBody)
                    if (todos.isSuccess)
                        todoCallback.onSuccess(todos.value)
                    else
                        todoCallback.onError(todos.message!!)
                }
            }
        }
    }

    override fun createPersonalTodo(
        todoPersonalRequest: TodoPersonalRequest, todoCallback: TodoCallback
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
                    todoCallback.onError(apiResult.errorMessage)
                }
                is ApiResult.Success -> {
                    val createTodoPersonalResponse = parsePersonalTodos(apiResult.responseBody)
                    if (createTodoPersonalResponse.isSuccess)
                        todoCallback.onSuccess()
                    else
                        todoCallback.onError(createTodoPersonalResponse.message!!)
                }
            }
        }
    }

    override fun createTeamTodo(
        todoPersonalRequest: TodoTeamRequest,
        todoCallback: TodoCallback
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
                    todoCallback.onError(apiResult.errorMessage)
                }
                is ApiResult.Success -> {
                    val response = parseTodoStatus(apiResult.responseBody)
                    if (response.isSuccess)
                        todoCallback.onSuccess(null)
                    else
                        todoCallback.onError(response.message!!)
                }
            }
        }
    }

    override fun changePersonalTodoStatus(
        todoStatus: TodoStatus,
        todoCallback: TodoCallback
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
                    todoCallback.onError(apiResult.errorMessage)
                }
                is ApiResult.Success -> {
                    val response = parseTodoStatus(apiResult.responseBody)
                    if (response.isSuccess)
                        todoCallback.onSuccess(null)
                    else
                        todoCallback.onError(response.message!!)
                }
            }
        }
    }

    override fun changeTeamTodoStatus(
        todoStatus: TodoStatus,
        todoCallback: TodoCallback
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
                is ApiResult.Failure -> todoCallback.onError(apiResult.errorMessage)

                is ApiResult.Success -> todoCallback.onSuccess(null)
            }
        }
    }

    override fun login(
        username: String, password: String,
        loginCallback: LoginCallback
    ) {
        val credentials = Credentials.basic(username, password)

        val request = Request.Builder()
            .url(LOGIN_ENDPOINT)
            .addHeader(AUTHORIZATION_HEADER, credentials)
            .build()

        okHttpClient.makeCall(request) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> {
                    loginCallback.onLoginFail(apiResult.errorMessage)
                }
                is ApiResult.Success -> {
                    val loginResponse = parseLoginResponse(apiResult.responseBody)
                    if (loginResponse.isSuccess)
                        loginResponse.value?.let { loginCallback.onLoginComplete(it,username = username) }
                    else
                        loginResponse.message?.let { loginCallback.onLoginFail(it) }
                }
            }
        }
    }

    override fun signUp(
        username: String, password: String, teamId: String,
        signUpCallback: SignUpCallback
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
                    signUpCallback.onSignUpFail(apiResult.errorMessage)
                }
                is ApiResult.Success -> {
                    val signUpResponse = parseSignUpResponse(apiResult.responseBody)
                    if (signUpResponse.isSuccess)
                        signUpCallback.onSignUpComplete()
                    else
                        signUpCallback.onSignUpFail(signUpResponse.message!!)
                }
            }
        }
    }
}
