package com.cheesecake.todo.data.network

import com.cheesecake.todo.data.network.Constants.BASE_URL
import com.cheesecake.todo.data.network.Constants.GET_METHOD
import com.cheesecake.todo.data.network.Constants.POST_METHOD
import com.cheesecake.todo.data.network.Constants.PUT_METHOD
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException


class MyHttpClient {


    private val client: OkHttpClient by lazy {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }


    @Throws(IOException::class)
    fun run(
        endpoint: String,
        method: String = "GET",
        requestBody: RequestBody? = null,
        headers: Map<String, String>? = null,
        callback: (String?, String?) -> Unit
    ) {
        val request = Request
            .Builder()
            .url(BASE_URL + endpoint)
            .apply {
                when (method) {
                    GET_METHOD -> get()
                    POST_METHOD -> post(requestBody!!)
                    PUT_METHOD -> put(requestBody!!)
                    else -> throw IllegalArgumentException("Invalid HTTP method: $method")
                }

                headers?.forEach { (key, value) ->
                    addHeader(key, value)
                }
            }
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
