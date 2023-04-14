package com.cheesecake.todo.data.network

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import okhttp3.MultipartBody
import org.json.JSONException
import org.json.JSONObject


fun parseTodos(json: String): List<TodoItem> {
    val todoItems = mutableListOf<TodoItem>()
    try {
        val jsonArray = JSONObject(json).getJSONArray("value")
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getString("id")
            val title = jsonObject.getString("title")
            val description = jsonObject.getString("description")
            val assignee = jsonObject.optString("assignee")
            val status = parseStatus(jsonObject.getInt("status"))
            val creationTime = jsonObject.getString("creationTime")
            val todoItem = TodoItem(id, title, description, assignee, status, creationTime)
            todoItems.add(todoItem)
        }
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return todoItems
}
fun parseStatus(status: Int): TodoState {
    return when (status) {
        0 -> TodoState.TODO
        1 -> TodoState.IN_PROGRESS
        2 -> TodoState.DONE
        else -> throw IllegalArgumentException("Invalid status value: $status")
    }
}

fun createMultipartBody(vararg fields: Pair<String, String?>) =
    MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .apply {
            fields.forEach { (key, value) ->
                if (value != null) {
                    addFormDataPart(key, value)
                }
            }
        }
        .build()

fun parseLoginResponse(response: String?): Pair<String, String>? {
    try {
        val json = JSONObject(response!!)
        val valueJson = json.optJSONObject("value")
        val token = valueJson?.optString("token")
        val expireAt = valueJson?.optString("expireAt")
        return Pair(token ?: return null, expireAt ?: return null)
    } catch (e: JSONException) {
        return null
    }
}

fun parseErrorMessageResponse(response: String): String = JSONObject(response).optString("message")