package com.cheesecake.todo.data.network

import com.cheesecake.todo.data.local.SharedPreferencesService
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val preferencesService: SharedPreferencesService) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val bearerToken = "Bearer ${preferencesService.getToken()}"
        val request = chain.request().newBuilder()
            .addHeader(AUTHORIZATION_HEADER, bearerToken)
            .build()
        return chain.proceed(request)
    }

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
    }

}