package com.cheesecake.todo.data.network

import android.util.Log
import com.cheesecake.todo.data.local.SharedPreferencesService
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val preferencesService: SharedPreferencesService) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val bearerToken = "Bearer ${preferencesService.getToken()}"
        val requestBuilder = chain.request().newBuilder()
        val token = preferencesService.getToken()
        Log.d("TAG", "intercept: token $token ")
        if (token != "") {
            Log.d("TAG", "intercept: token $token ")
            requestBuilder.addHeader(AUTHORIZATION_HEADER, bearerToken)
        }
        return chain.proceed(requestBuilder.build())
    }

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
    }

}