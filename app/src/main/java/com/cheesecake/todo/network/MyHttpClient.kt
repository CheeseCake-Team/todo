package com.cheesecake.todo.network

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

class MyHttpClient(private val token: String) {
    private val client: OkHttpClient by lazy {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }
    private val baseUrl = "https://team-todo-62dmq.ondigitalocean.app"

    @Throws(IOException::class)
    fun run(
        path: String,
        method: String = "GET",
        requestBody: MultipartBody? = null,
        callback: (String?, String?) -> Unit
    ) {
        val requestBuilder = Request.Builder()
            .url(baseUrl + path)

        when (method) {
            "GET" -> requestBuilder.get()
            "POST" -> requestBuilder.post(
                requestBody ?: throw IllegalArgumentException("Request body required for POST")
                //put
            )
            else -> throw IllegalArgumentException("Invalid HTTP method: $method")
        }

        val request = requestBuilder
            .addHeader("Authorization", "Bearer $token")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                if (!response.isSuccessful) {
                    callback(null, "Unexpected code ${response.code}: $body")
                } else {
                    callback(body, null)
                }
            }
        })
    }
}
