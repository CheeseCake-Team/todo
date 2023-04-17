package com.cheesecake.todo.data.network

import android.content.Context
import com.cheesecake.todo.data.local.SharedPreferencesService
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val sharedPreferencesService: SharedPreferencesService) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val bearerToken = "Bearer ${sharedPreferencesService.getToken()}"
        val request = chain.request().newBuilder()
            .addHeader(AUTHORIZATION, bearerToken)
            .build()
        return chain.proceed(request)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}