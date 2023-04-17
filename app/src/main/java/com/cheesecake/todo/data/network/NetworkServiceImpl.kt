package com.cheesecake.todo.data.network

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.utils.Constants.BASE_URL
import com.cheesecake.todo.utils.makeCall
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.Request


class NetworkServiceImpl(private val okHttpClient: OkHttpClient) : NetworkService {

    override fun getPersonalTodos(
        callback: (List<TodoItem>?, String?) -> Unit
    ) {
        val request = Request.Builder()
            .url("$BASE_URL/todo/personal")
            .build()

        okHttpClient.makeCall(request) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> {
                    callback(null, apiResult.errorMessage)
                }
                is ApiResult.Success<> -> {
                    val todos = parseTodos(apiResult.responseBody)
                    callback(todos, null)
                }
            }
        }
    }

    override fun getTeamTodos(
        callback: (List<TodoItem>?, String?) -> Unit
    ) {
        val request = Request.Builder()
            .url("$BASE_URL/todo/team")
            .build()

        okHttpClient.makeCall(request) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> {
                    callback(null, apiResult.errorMessage)
                }
                is ApiResult.Success<> -> {
                    val todos = parseTodos(apiResult.responseBody)
                    callback(todos, null)
                }
            }
        }
    }

    override fun createTodo(
        title: String,
        description: String,
        assignee: String?,
        isPersonal: Boolean,
        token: String,
        callback: (String?) -> Unit
    ) {
        val endpoint = if (isPersonal) "/todo/personal" else "/todo/team"
        val requestBody = createMultipartBody(
            "title" to title, "description" to description, "assignee" to assignee
        )
        val headers = mapOf("Authorization" to "Bearer $token")

        okHttpClient.makeCall(endpoint, "POST", requestBody, headers) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> {
                    callback(apiResult.errorMessage)
                }
                is ApiResult.Success -> {
                    callback(null)
                }
            }
        }
    }

    override fun changeTodoStatus(
        todoId: String,
        newStatus: TodoState,
        isPersonal: Boolean,
        token: String,
        callback: (String?) -> Unit
    ) {
        val requestBody = createMultipartBody(
            "id" to todoId, "status" to newStatus.value.toString()
        )
        val endpoint = if (isPersonal) "/todo/personal" else "/todo/team"
        val headers = mapOf("Authorization" to "Bearer $token")
        okHttpClient.makeCall(endpoint, "PUT", requestBody, headers) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> {
                    callback(apiResult.errorMessage)
                }
                is ApiResult.Success -> {
                    callback(null)
                }
            }
        }
    }

    override fun login(
        username: String,
        password: String,
        callback: (Pair<String, String>?, String?) -> Unit
    ) {
        val credentials = Credentials.basic(username, password)

        okHttpClient.makeCall(
            "/login",
            "GET",
            headers = mapOf("Authorization" to credentials)
        ) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> {
                    callback(null, apiResult.errorMessage)
                }
                is ApiResult.Success -> {
                    val token = parseLoginResponse(apiResult.responseBody)
                    callback(token, null)
                }
            }
        }
    }

    override fun signUp(
        username: String,
        password: String,
        teamId: String,
        signUpCallback: (String?) -> Unit
    ) {
        val requestBody = createMultipartBody(
            "username" to username, "password" to password, "teamId" to teamId
        )
        okHttpClient.makeCall("/signup", "POST", requestBody) { apiResult ->
            when (apiResult) {
                is ApiResult.Failure -> {
                    signUpCallback(apiResult.errorMessage)
                }
                is ApiResult.Success -> {
                    signUpCallback(null)
                }
            }
        }
    }

    companion object {
        //TODO
        // constant keys
    }
}
