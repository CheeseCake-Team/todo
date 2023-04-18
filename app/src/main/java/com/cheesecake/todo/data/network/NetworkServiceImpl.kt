package com.cheesecake.todo.data.network

import android.util.Log
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.request.TodoPersonalRequest
import com.cheesecake.todo.data.models.request.TodoStatusRequest
import com.cheesecake.todo.data.models.request.TodoTeamRequest
import com.cheesecake.todo.data.models.response.LoginValue
import com.cheesecake.todo.data.models.response.SignUpValue
import com.cheesecake.todo.data.models.response.TodosValue
import com.cheesecake.todo.data.repository.identity.LoginCallback
import com.cheesecake.todo.data.repository.identity.SignUpCallback
import com.cheesecake.todo.data.repository.todos.TodoCallback
import com.cheesecake.todo.data.network.AuthorizationInterceptor.Companion.AUTHORIZATION_HEADER
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
                    val todos = parseResponse<TodosValue>(apiResult.responseBody)
                    if (todos.isSuccess)
                        todoCallback.onSuccessPersonalTodo(todos.value!!.value)
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
                    val todos = parseResponse<List<TodoItem>>(apiResult.responseBody)
                    if (todos.isSuccess)
                        todoCallback.onSuccessTeamTodo(todos.value)
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
                    val createTodoPersonalResponse = parseResponse<List<TodoItem>>(apiResult.responseBody)
                    if (createTodoPersonalResponse.isSuccess)
                        todoCallback.onSuccessPersonalTodo()
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
                    val response = parseResponse<TodoItem>(apiResult.responseBody)
                    if (response.isSuccess)
                        todoCallback.onSuccessTeamTodo(null)
                    else
                        todoCallback.onError(response.message!!)
                }
            }
        }
    }

    override fun changePersonalTodoStatus(
        todoStatusRequest: TodoStatusRequest,
        todoCallback: TodoCallback
    ) {
        val requestBody = createMultipartBody(
            "id" to todoStatusRequest.todoId, "status" to todoStatusRequest.newStatus.value.toString()
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
                    val response = parseResponse<String>(apiResult.responseBody)
                    if (response.isSuccess)
                        todoCallback.onSuccessTeamTodo(null)
                    else
                        todoCallback.onError(response.message!!)
                }
            }
        }
    }

    override fun changeTeamTodoStatus(
        todoStatusRequest: TodoStatusRequest,
        todoCallback: TodoCallback
    ) {
        val requestBody = createMultipartBody(
            "id" to todoStatusRequest.todoId, "status" to todoStatusRequest.newStatus.value.toString()
        )

        val request = Request.Builder()
            .url(TEAM_ENDPOINT)
            .put(requestBody)
            .build()

        okHttpClient.makeCall(request) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> todoCallback.onError(apiResult.errorMessage)

                is ApiResult.Success -> todoCallback.onSuccessTeamTodo(null)
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
                    val loginResponse = parseResponse<LoginValue>(apiResult.responseBody)
                    Log.d("login: ", loginResponse.toString())
                    if (loginResponse.isSuccess)
                        loginResponse.value?.let { loginCallback.onLoginComplete(it) }
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
                    val signUpResponse = parseResponse<SignUpValue>(apiResult.responseBody)
                    if (signUpResponse.isSuccess)
                        signUpCallback.onSignUpComplete()
                    else
                        signUpCallback.onSignUpFail(signUpResponse.message!!)
                }
            }
        }
    }

    private companion object {
        const val LOGIN_ENDPOINT = "https://team-todo-62dmq.ondigitalocean.app/login"
        const val SIGNUP_ENDPOINT = "https://team-todo-62dmq.ondigitalocean.app/signup"
        const val PERSONAL_ENDPOINT = "https://team-todo-62dmq.ondigitalocean.app/todo/personal"
        const val TEAM_ENDPOINT = "https://team-todo-62dmq.ondigitalocean.app/todo/team"
    }
}
