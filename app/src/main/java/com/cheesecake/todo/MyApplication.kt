package com.cheesecake.todo

import android.app.Application
import android.content.Context
import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.local.SharedPreferencesServiceImpl
import com.cheesecake.todo.data.network.AuthorizationInterceptor
import com.cheesecake.todo.data.network.NetworkInterceptor
import com.cheesecake.todo.data.network.identity.IdentityNetworkServiceImpl
import com.cheesecake.todo.data.network.todos.TodoNetworkServiceImpl
import com.cheesecake.todo.data.repository.identity.IdentityRepository
import com.cheesecake.todo.data.repository.identity.IdentityRepositoryFactory
import com.cheesecake.todo.data.repository.identity.IdentityRepositoryImpl
import com.cheesecake.todo.data.repository.todos.TodoRepository
import com.cheesecake.todo.data.repository.todos.TodoRepositoryFactory
import com.cheesecake.todo.data.repository.todos.TodoRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class MyApplication : Application(), IdentityRepositoryFactory, TodoRepositoryFactory {

    private lateinit var identityRepository: IdentityRepository
    private lateinit var todoRepository: TodoRepository

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
            .addInterceptor(AuthorizationInterceptor(sharedPreferencesService))
            .addInterceptor(NetworkInterceptor(applicationContext))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        val todoNetworkService = TodoNetworkServiceImpl(okHttpClient)
        todoRepository = TodoRepositoryImpl(todoNetworkService, sharedPreferencesService)

        val identityNetworkService = IdentityNetworkServiceImpl(okHttpClient)
        identityRepository =
            IdentityRepositoryImpl(identityNetworkService, sharedPreferencesService)
    }

    override fun createAuthRepository(): IdentityRepository {
        return identityRepository
    }

    override fun createTodoRepository(): TodoRepository {
        return todoRepository
    }

    companion object {
        const val PREFS_NAME = "Shared Preference"
    }
}
