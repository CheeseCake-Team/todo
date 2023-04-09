package com.cheesecake.todo.data.network

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.data.network.Constants.AUTHORIZATION_HEADER
import com.cheesecake.todo.data.network.Constants.GET_METHOD
import com.cheesecake.todo.data.network.Constants.PERSONAL_ENDPOINT
import com.cheesecake.todo.data.network.Constants.POST_METHOD
import com.cheesecake.todo.data.network.Constants.PUT_METHOD
import com.cheesecake.todo.data.network.Constants.TEAM_ENDPOINT
import okhttp3.Credentials


class NetworkServiceImpl private constructor() : NetworkService {

    companion object {
        private var instance: NetworkServiceImpl? = null

        fun getInstance(): NetworkServiceImpl {
            if (instance == null) {
                instance = NetworkServiceImpl()
            }
            return instance!!
        }
    }

    private val okHttpClient = MyHttpClient()

    override fun getTodos(
        isPersonal: Boolean,
        token: String,
        callback: (List<TodoItem>?, String?) -> Unit
    ) {
        val endpoint = if (isPersonal) PERSONAL_ENDPOINT else TEAM_ENDPOINT
        val headers = mapOf(AUTHORIZATION_HEADER to "Bearer $token")

        okHttpClient.run(endpoint, GET_METHOD, headers = headers) { response, error ->
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
        val endpoint = if (isPersonal) PERSONAL_ENDPOINT else TEAM_ENDPOINT
        val requestBody = createMultipartBody(
            "title" to title, "description" to description, "assignee" to assignee
        )
        val headers = mapOf(AUTHORIZATION_HEADER to "Bearer $token")

        okHttpClient.run(endpoint, POST_METHOD, requestBody, headers) { _, error ->
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
        val endpoint = if (isPersonal) PERSONAL_ENDPOINT else TEAM_ENDPOINT
        val headers = mapOf(AUTHORIZATION_HEADER to "Bearer $token")
        okHttpClient.run(endpoint, PUT_METHOD, requestBody, headers) { _, error ->
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

        okHttpClient.run(
            Constants.LOGIN_ENDPOINT,
            GET_METHOD,
            headers = mapOf(AUTHORIZATION_HEADER to credentials)
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
        callback: (Pair<String, String>?, String?) -> Unit
    ) {
        val requestBody = createMultipartBody(
            "username" to username, "password" to password, "teamId" to teamId
        )
        okHttpClient.run(Constants.SIGNUP_ENDPOINT, POST_METHOD, requestBody) { response, error ->
            if (error != null) {
                callback(null, error)
            } else {
                val responseBody = response?.let { json ->
                    parseSignupResponse(json)
                }
                callback(responseBody, null)
            }
        }
    }

}