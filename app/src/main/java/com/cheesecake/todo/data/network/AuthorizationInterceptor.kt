package com.cheesecake.todo.data.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val bearerToken = "Bearer $token"
        val request = chain.request().newBuilder()
            .addHeader(AUTHORIZATION_HEADER, bearerToken)
            .build()
        return chain.proceed(request)
    }

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
    }

}