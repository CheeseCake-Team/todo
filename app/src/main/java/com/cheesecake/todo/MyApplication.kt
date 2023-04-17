package com.cheesecake.todo

import android.app.Application
import android.content.Context
import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.local.SharedPreferencesServiceImpl
import com.cheesecake.todo.data.network.AuthorizationInterceptor
import com.cheesecake.todo.data.network.NetworkServiceImpl
import com.cheesecake.todo.data.repository.identity.AuthRepository
import com.cheesecake.todo.data.repository.identity.AuthRepositoryFactory
import com.cheesecake.todo.data.repository.identity.AuthRepositoryImpl
import com.cheesecake.todo.utils.Constants.PREFS_NAME
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

class MyApplication : Application(), AuthRepositoryFactory {

    private lateinit var authRepository: AuthRepository
    private val sharedPreferencesService: SharedPreferencesService by lazy {
        SharedPreferencesServiceImpl(
            getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        )
    }
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
//            .addInterceptor { chain ->
//                if (!isConnectedToNetwork()) {
//                    throw IOException("No internet connection")
//                }
//                chain.proceed(chain.request())
//            }
            .addInterceptor(AuthorizationInterceptor(sharedPreferencesService))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        val networkService = NetworkServiceImpl(okHttpClient)
        authRepository = AuthRepositoryImpl(networkService, sharedPreferencesService)
    }

    override fun createAuthRepository(): AuthRepository {
        return authRepository
    }
}
