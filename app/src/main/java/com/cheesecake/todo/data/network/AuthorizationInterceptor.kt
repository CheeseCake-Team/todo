package com.cheesecake.todo.data.network

import android.util.Log
import com.cheesecake.todo.utils.Constants.AUTHORIZATION_HEADER
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val bearerToken = "Bearer $token"
        val request = chain.request().newBuilder()
            .addHeader(AUTHORIZATION_HEADER, bearerToken)
            .build()
        Log.d("TAG", "intercept: $token")
        return chain.proceed(request)
    }

}