package com.cheesecake.todo.network

import com.cheesecake.todo.utils.Constants.GET_METHOD
import com.cheesecake.todo.utils.Constants.PERSONAL_ENDPOINT
import com.cheesecake.todo.utils.Constants.POST_METHOD
import com.cheesecake.todo.utils.Constants.PUT_METHOD
import com.cheesecake.todo.utils.Constants.TEAM_ENDPOINT

class NetworkService private constructor(private val okHttpClient: MyHttpClient) {

    companion object {
        private var instance: NetworkService? = null

        fun getInstance(okHttpClient: MyHttpClient): NetworkService {
            if (instance == null) {
                instance = NetworkService(okHttpClient)
            }
            return instance!!
        }
    }


    fun getTodos(isPersonal: Boolean, callback: (List<Todo>?, String?) -> Unit) {
        val endpoint = if (isPersonal) PERSONAL_ENDPOINT else TEAM_ENDPOINT
        okHttpClient.run(endpoint, GET_METHOD) { response, error ->
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

    fun createTodo(
        title: String,
        description: String,
        assignee: String?,
        isPersonal: Boolean,
        callback: (String?) -> Unit
    ) {
        val endpoint = if (isPersonal) PERSONAL_ENDPOINT else TEAM_ENDPOINT
        val requestBody = createTodoRequestBody(title, description, assignee)

        okHttpClient.run(endpoint, POST_METHOD, requestBody) { _, error ->
            if (error != null) {
                callback(error)
            } else {
                callback(null)
            }
        }
    }

    fun changeTodoStatus(
        todoId: String, newStatus: Int, isPersonal: Boolean, callback: (String?) -> Unit
    ) {
        val requestBody = createTodoRequestBodyForPut(todoId, newStatus)
        val endpoint = if (isPersonal) PERSONAL_ENDPOINT else TEAM_ENDPOINT
        okHttpClient.run(endpoint, PUT_METHOD, requestBody) { _, error ->
            if (error != null) {
                callback(error)
            } else {
                callback(null)
            }
        }
    }
}
