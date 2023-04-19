package com.cheesecake.todo.data.network

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.request.TodoPersonalRequest
import com.cheesecake.todo.data.models.request.TodoStatusRequest
import com.cheesecake.todo.data.models.request.TodoTeamRequest
import com.cheesecake.todo.data.models.response.LoginValue
import com.cheesecake.todo.data.models.response.SignUpValue
import com.cheesecake.todo.data.network.AuthorizationInterceptor.Companion.AUTHORIZATION_HEADER
import com.cheesecake.todo.utils.makeCall
import okhttp3.Credentials
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request

class NetworkServiceImpl(private val okHttpClient: OkHttpClient) : NetworkService {
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
        val requestBody = createMultipartBody(
            "title" to todoPersonalRequest.title, "description" to todoPersonalRequest.description
        )
        val request = Request.Builder().url(PERSONAL_ENDPOINT).post(requestBody).build()

        okHttpClient.makeCall<TodoItem>(request, responseCallback)
    }

    override fun createTeamTodo(
        todoPersonalRequest: TodoTeamRequest, responseCallback: ResponseCallback
    ) {
        val requestBody = createMultipartBody(
            "title" to todoPersonalRequest.title,
            "description" to todoPersonalRequest.description,
            "assignee" to todoPersonalRequest.assignee
        )

        val request = Request.Builder().url(TEAM_ENDPOINT).post(requestBody).build()

        okHttpClient.makeCall<TodoItem>(request, responseCallback)
    }

    override fun updatePersonalTodoStatus(
        todoStatusRequest: TodoStatusRequest, responseCallback: ResponseCallback
    ) {
        val requestBody = createMultipartBody(
            "id" to todoStatusRequest.todoId,
            "status" to todoStatusRequest.newStatus.value.toString()
        )

        val request = Request.Builder().url(PERSONAL_ENDPOINT).put(requestBody).build()

        okHttpClient.makeCall<String>(request, responseCallback)
    }

    override fun updateTeamTodoStatus(
        todoStatusRequest: TodoStatusRequest, responseCallback: ResponseCallback
    ) {
        val requestBody = createMultipartBody(
            "id" to todoStatusRequest.todoId,
            "status" to todoStatusRequest.newStatus.value.toString()
        )

        val request = Request.Builder().url(TEAM_ENDPOINT).put(requestBody).build()

        okHttpClient.makeCall<String>(request, responseCallback)
    }

    override fun login(
        username: String, password: String, responseCallback: ResponseCallback
    ) {
        val credentials = Credentials.basic(username, password)

        val request =
            Request.Builder().url(LOGIN_ENDPOINT).addHeader(AUTHORIZATION_HEADER, credentials)
                .build()
        okHttpClient.makeCall<LoginValue>(request, responseCallback)
    }

    override fun signUp(
        username: String, password: String, teamId: String, responseCallback: ResponseCallback
    ) {
        val requestBody = createMultipartBody(
            "username" to username, "password" to password, "teamId" to teamId
        )

        val request = Request.Builder().url(SIGNUP_ENDPOINT).post(requestBody).build()

        okHttpClient.makeCall<SignUpValue>(request, responseCallback)
    }

    private fun createMultipartBody(vararg fields: Pair<String, String?>) =
        MultipartBody.Builder().setType(MultipartBody.FORM).apply {
            fields.forEach { (key, value) ->
                if (value != null) {
                    addFormDataPart(key, value)
                }
            }
        }.build()


    private companion object {
        const val LOGIN_ENDPOINT = "https://team-todo-62dmq.ondigitalocean.app/login"
        const val SIGNUP_ENDPOINT = "https://team-todo-62dmq.ondigitalocean.app/signup"
        const val PERSONAL_ENDPOINT = "https://team-todo-62dmq.ondigitalocean.app/todo/personal"
        const val TEAM_ENDPOINT = "https://team-todo-62dmq.ondigitalocean.app/todo/team"
    }
}
