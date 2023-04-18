package com.cheesecake.todo.ui.createtodo

import android.content.Context
import com.cheesecake.todo.data.local.SharedPreferencesServiceImpl
import com.cheesecake.todo.ui.createtodo.model.CreateToDoModel
import com.cheesecake.todo.ui.createtodo.presenter.StatusResponse
import com.cheesecake.todo.utils.Constants
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


class CreateTodoPresenter(var context: Context) {
    private val baseUrl = "https://team-todo-62dmq.ondigitalocean.app/todo/"
    private val endPointPersonal = "personal"
    private val endPointTeam = "team"
    private lateinit var sharedPreferencesServiceImpl: SharedPreferencesServiceImpl
    private val client = OkHttpClient()

    private fun requestBody(title: String, description: String) =
        FormBody.Builder().apply {
            add("title", title)
            add("description", description)
        }.build()


    fun toDoForPersonal(
        title: String,
        description: String,
        token: String,
        callback: StatusResponse
    ) {
        request(endPointPersonal, requestBody(title, description), token, callback)
    }

    fun toDoForTeam(
        title: String,
        description: String,
        token: String,
        callback: StatusResponse
    ) {
        request(endPointTeam, requestBody(title, description), token, callback)
    }

    private fun request(
        endPoint: String,
        requestBody: RequestBody,
        token: String,
        callback: StatusResponse
    ) {
        val request = Request.Builder().url(baseUrl + endPoint)
            .apply {
                post(requestBody)
                    .addHeader("Authorization", "Bearer $token")
            }.build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    val jason = Gson().fromJson(body, CreateToDoModel::class.java)
                    callback.onSuccess(jason)
                } else {
                    callback.onError("Lost NetWork")
                }
            }
        })
    }
     fun checkToken() : String?{
        sharedPreferencesServiceImpl = SharedPreferencesServiceImpl(
            context.getSharedPreferences(
                Constants.PREFS_NAME,
                Context.MODE_PRIVATE
            )
        )
       /*sharedPreferencesServiceImpl.saveTokenAndExpireDate("")
         removeTokenAndExpiryIfTokenInvalid(sharedPreferencesServiceImpl)
        return  sharedPreferencesServiceImpl.getToken()*/
         return ""
    }


}