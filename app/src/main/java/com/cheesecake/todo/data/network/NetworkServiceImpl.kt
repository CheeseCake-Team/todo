package com.cheesecake.todo.data.network

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import okhttp3.Credentials


class NetworkServiceImpl : NetworkService {

    override fun getTodos(
        isPersonal: Boolean,
        token: String,
        callback: (List<TodoItem>?, String?) -> Unit
    ) {
        val endpoint = if (isPersonal) "/todo/personal" else "/todo/team"
        val headers = mapOf("Authorization" to "Bearer $token")

        ApiClient.makeCall(endpoint, "GET", headers = headers) { response, error ->
            if (error != null) {
                callback(null, error)
            } else {
                val todos = response?.let { json ->
                    parseTodos(json)
                }
                callback(todos, null)
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

        ApiClient.makeCall(endpoint, "POST", requestBody, headers) { _, error ->
            if (error != null) {
                callback(error)
            } else {
                callback(null)
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
        ApiClient.makeCall(endpoint, "PUT", requestBody, headers) { _, error ->
            if (error != null) {
                callback(error)
            } else {
                callback(null)
            }
        }
    }

    override fun login(
        username: String, password: String, callback: (Pair<String, String>?, String?) -> Unit
    ) {
        val credentials = Credentials.basic(username, password)

        ApiClient.makeCall(
            "/login",
            "GET",
            headers = mapOf("Authorization" to credentials)
        ) { response, error ->
            if (error != null) {
                callback(null, error)
            } else {
                val token = response?.let { json ->
                    parseLoginResponse(json)
                }
                callback(token, null)
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
        ApiClient.makeCall("/signup", "POST", requestBody) { apiResult ->
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
}
