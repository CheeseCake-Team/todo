package com.cheesecake.todo.network

import okhttp3.MultipartBody
import org.json.JSONException
import org.json.JSONObject


/**
 * Parses a JSON string into a list of Todo objects.
 * @param json The JSON string to parse.
 * @return A list of Todo objects.
 */
fun parseTodos(json: String): List<Todo> {
    val todos = mutableListOf<Todo>()
    try {
        val jsonArray = JSONObject(json).getJSONArray("value")
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getString("id")
            val title = jsonObject.getString("title")
            val description = jsonObject.getString("description")
            val assignee = jsonObject.optString("assignee")
            val status = jsonObject.getInt("status")
            val creationTime = jsonObject.getString("creationTime")
            val todo = Todo(id, title, description, assignee, status, creationTime)
            todos.add(todo)
        }
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return todos
}


/**
 * Creates a multipart request body for creating a new Todo.
 * @param title The title of the Todo.
 * @param description The description of the Todo.
 * @param assignee The assignee of the Todo, if any.
 * @return A MultipartBody object representing the request body.
 */
fun createTodoRequestBody(
    title: String,
    description: String,
    assignee: String?
) = MultipartBody.Builder().setType(MultipartBody.FORM)
    .addFormDataPart("title", title)
    .addFormDataPart("description", description).apply {
        if (assignee != null) {
            addFormDataPart("assignee", assignee)
        }
    }.build()

