package com.cheesecake.todo.network

import okhttp3.MultipartBody

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


    fun getTeamTodos(callback: (List<TeamTodoItem>?, String?) -> Unit) {
        okHttpClient.run("/todo/team", "GET") { response, error ->
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

    fun createPersonalTodo(
        title: String, description: String, assignee: String, callback: (String?) -> Unit
    ) {
        val requestBody =
            MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("title", title)
                .addFormDataPart("description", description).addFormDataPart("assignee", assignee)
                .build()

        okHttpClient.run("/todo/personal", "POST", requestBody) { _, error ->
            if (error != null) {
                callback(error)
            } else {
                callback(null)
            }
        }
    }


}
