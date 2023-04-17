package com.cheesecake.todo

import android.app.Application
import android.content.Context
import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.local.SharedPreferencesServiceImpl
import com.cheesecake.todo.data.network.AuthorizationInterceptor
import com.cheesecake.todo.data.network.NetworkServiceImpl
import com.cheesecake.todo.data.repository.identity.IdentityRepository
import com.cheesecake.todo.data.repository.identity.IdentityRepositoryFactory
import com.cheesecake.todo.data.repository.identity.IdentityRepositoryImpl
import com.cheesecake.todo.utils.Constants.PREFS_NAME
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class MyApplication : Application(), IdentityRepositoryFactory {

    private lateinit var identityRepository: IdentityRepository

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
            .addInterceptor(AuthorizationInterceptor(sharedPreferencesService.getToken()!!))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        val networkService = NetworkServiceImpl(okHttpClient)
        identityRepository = IdentityRepositoryImpl(networkService, sharedPreferencesService)
    }

    override fun createAuthRepository(): IdentityRepository {
        return identityRepository
    }
}
