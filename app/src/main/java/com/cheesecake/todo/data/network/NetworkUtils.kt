package com.cheesecake.todo.data.network

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

fun parseSignupResponse(response: String?): Pair<String, String>? {
    try {
        val json = JSONObject(response!!)
        val valueJson = json.optJSONObject("value")
        val token = valueJson?.optString("userId")
        val expireAt = valueJson?.optString("username")
        return Pair(token ?: return null, expireAt ?: return null)
    } catch (e: JSONException) {
        return null
    }
}
