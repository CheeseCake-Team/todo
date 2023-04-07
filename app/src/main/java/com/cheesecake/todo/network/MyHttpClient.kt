package com.cheesecake.todo.network

import com.cheesecake.todo.utils.Constants.BASE_URL
import com.cheesecake.todo.utils.Constants.GET_METHOD
import com.cheesecake.todo.utils.Constants.POST_METHOD
import com.cheesecake.todo.utils.Constants.PUT_METHOD
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException


/**
 * A class that helps send HTTP requests to a server using the OkHttp library.
 *
 * @property token A string that represents an authorization token for accessing the server.
 */
class MyHttpClient(private val token: String) {


    /**
    * An object of the `OkHttpClient` class that's used to make HTTP requests.
    */
    private val client: OkHttpClient by lazy {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * A string that represents the base URL of the server.
     */
    private val baseUrl = "https://team-todo-62dmq.ondigitalocean.app"

    /**
     * Sends an HTTP request to the server.
     *
     * @param path A string that represents the path on the server that the request should be sent to (e.g. "/users").
     * @param method A string that represents the HTTP method that should be used for the request (e.g. "GET", "POST", "PUT").
     * It defaults to "GET" if not specified.
     * @param requestBody An object of the `MultipartBody` class that represents the body of the request (e.g.
     * the data that should be sent to the server). It's optional and only used for "POST" requests.
     * @param callback A function that gets called when the server responds to the request. It takes two arguments:
     * `body`: A string that represents the response body (e.g. the data that the server sends back). It's `null` if there's an error.
     * `error`: A string that represents any error message that occurs during the request. It's `null` if there's no error.
     *
     * @throws [IOException] If there's an error during the request.
     * @throws [IllegalArgumentException] If an invalid HTTP method is used or a request body is required for "POST" requests
     * but not provided.
     */
    @Throws(IOException::class)
    fun run(
        endpoint: String,
        method: String = "GET",
        requestBody: RequestBody? = null,
        callback: (String?, String?) -> Unit
    ) {
        val requestBuilder = Request.Builder()
            .url(BASE_URL + endpoint)

        when (method) {
            GET_METHOD -> requestBuilder.get()
            POST_METHOD -> requestBuilder.post(
                requestBody ?: throw IllegalArgumentException("Request body required for POST")
            )
            PUT_METHOD -> requestBuilder.put(
                requestBody ?: throw IllegalArgumentException("Request body required for POST")
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
