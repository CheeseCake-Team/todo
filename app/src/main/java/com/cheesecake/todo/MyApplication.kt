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

    private val todosOkHttpClient: OkHttpClient by lazy {
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(AuthorizationInterceptor(sharedPreferencesService.getToken()!!))
            .build()
    }

    private val identityOkHttpClient: OkHttpClient by lazy {
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        val networkService = NetworkServiceImpl(todosOkHttpClient)
        val identityNetworkService = NetworkServiceImpl(identityOkHttpClient)

        identityRepository =
            IdentityRepositoryImpl(identityNetworkService, sharedPreferencesService)
        todoRepository = TodoRepositoryImpl(networkService, sharedPreferencesService)
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
