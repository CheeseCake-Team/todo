package com.cheesecake.todo.data.network

import android.util.Log
import com.cheesecake.todo.utils.Constants.AUTHORIZATION_HEADER
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = if (token == "") {
            Log.e("intercept: ", token)
            val bearerToken = "Bearer $token"
             chain.request().newBuilder()
                .addHeader(AUTHORIZATION_HEADER, bearerToken)
                .build()
        } else {
            Log.e("intercept: ", token)
            chain.request().newBuilder()
                //.addHeader(AUTHORIZATION_HEADER, bearerToken)
                .build()
        }
        return chain.proceed(request)
    }

}