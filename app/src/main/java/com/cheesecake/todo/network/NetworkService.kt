package com.cheesecake.todo.network


/**

This class provides a network service for communicating with a remote API to fetch and create todos.
@param okHttpClient An instance of MyHttpClient that is used to make HTTP requests to the remote API.
 */
class NetworkService private constructor(private val okHttpClient: MyHttpClient) {

    companion object {
        private var instance: NetworkService? = null


        /**
         * This method returns an instance of the NetworkService class.
         *
         * @param okHttpClient An instance of MyHttpClient that is used to make HTTP requests to the remote API.
         *
         * @return An instance of the NetworkService class.
         */
        fun getInstance(okHttpClient: MyHttpClient): NetworkService {
            if (instance == null) {
                instance = NetworkService(okHttpClient)
            }
            return instance!!
        }
    }


    /**
     * This method retrieves todos from the remote API.
     *
     * @param isPersonal A boolean value indicating whether to fetch personal or team todos.
     * @param callback A function that will be called when the request completes, passing in the retrieved todos or an error message.
     *
     */
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

    /**
     * This method creates a new todo on the remote API.
     *
     * @param title The title of the new todo.
     * @param description The description of the new todo.
     * @param assignee The name of the person to whom the new todo is assigned (optional).
     * @param isPersonal A boolean value indicating whether to create a personal or team todo.
     * @param callback A function that will be called when the request completes, passing in an error message if applicable.
     *
     */
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
