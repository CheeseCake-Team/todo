package com.cheesecake.todo.network

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
        val path = if (isPersonal) "/todo/personal" else "/todo/team"
        okHttpClient.run(path, "GET") { response, error ->
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
        val endpoint = if (isPersonal) "/todo/personal" else "/todo/team"
        val requestBody = createTodoRequestBody(title, description, assignee)

        okHttpClient.run(endpoint, "POST", requestBody) { _, error ->
            if (error != null) {
                callback(error)
            } else {
                callback(null)
            }
        }
    }

}
