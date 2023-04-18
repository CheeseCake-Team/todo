package com.cheesecake.todo.data.network

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.models.response.LoginResponse
import com.cheesecake.todo.data.models.response.SignUpResponse
import com.cheesecake.todo.data.models.response.TodoPersonalResponse
import com.cheesecake.todo.data.models.response.TodoTeamResponse
import com.google.gson.Gson
import okhttp3.MultipartBody
import org.json.JSONException
import org.json.JSONObject

private val gson = Gson()
fun parseTeamTodos(response: String) = gson.fromJson(response,
    TodoTeamResponse::class.java)

fun parsePersonalTodos(response: String) = gson.fromJson(response,
    TodoPersonalResponse::class.java)

fun parseStatus(status: Int): TodoState {
    return when (status) {
        0 -> TodoState.TODO
        1 -> TodoState.IN_PROGRESS
        2 -> TodoState.DONE
        else -> throw IllegalArgumentException("Invalid status value: $status")
    }
}

fun parseTodoStatus(response: String?): BaseResponse<*> = gson.fromJson(response, BaseResponse::class.java)

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

fun parseLoginResponse(response: String?): LoginResponse = gson.fromJson(response, LoginResponse::class.java)

fun parseSignUpResponse(response: String?): SignUpResponse = gson.fromJson(response,
    SignUpResponse::class.java)