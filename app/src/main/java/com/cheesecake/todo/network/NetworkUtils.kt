package com.cheesecake.todo.network

import okhttp3.MultipartBody
import org.json.JSONException
import org.json.JSONObject

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

