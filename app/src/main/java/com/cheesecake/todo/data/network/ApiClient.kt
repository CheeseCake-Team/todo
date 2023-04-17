package com.cheesecake.todo.data.network

import android.util.Log
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException


object ApiClient {

    private val client: OkHttpClient by lazy {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    private const val baseUrl = "https://team-todo-62dmq.ondigitalocean.app"

    @Throws(IOException::class)
    fun makeCall(
        endpoint: String,
        method: String = "GET",
        requestBody: RequestBody? = null,
        headers: Map<String, String>? = null,
        callback: (ApiResult) -> Unit
    ) {
        val request = Request.Builder().url(baseUrl + endpoint).apply {
            when (method) {
                "GET" -> get()
                "POST" -> post(requestBody!!)
                "PUT" -> put(requestBody!!)
                else -> throw IllegalArgumentException("Invalid HTTP method: $method")
            }

            headers?.forEach { (key, value) ->
                addHeader(key, value)
            }
        }.build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(ApiResult.Failure(e.message ?: "Unknown error"))
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                if (!response.isSuccessful) {
                    callback(ApiResult.Failure(parseErrorMessageResponse(body ?: "")))
                    Log.d("TAG", "onResponse:Failure ")
                } else {
                    callback(ApiResult.Success(body ?: ""))
                    Log.d("TAG", "onResponse:Success ")
                }
            }
        })
    }
}
