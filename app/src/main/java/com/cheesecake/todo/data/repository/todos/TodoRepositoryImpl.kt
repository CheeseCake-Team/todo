package com.cheesecake.todo.data.repository.todos

import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.data.models.request.TodoPersonalRequest
import com.cheesecake.todo.data.models.request.TodoStatusRequest
import com.cheesecake.todo.data.models.request.TodoTeamRequest
import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.network.todos.TodoNetworkService
import com.cheesecake.todo.data.repository.BaseRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class TodoRepositoryImpl(
    private val networkDataSource: TodoNetworkService,
    private val sharedPreferencesService: SharedPreferencesService
) : TodoRepository, BaseRepository() {

    override fun getTeamTodos(
        onSuccess: (BaseResponse<List<TodoItem>>) -> Unit,
        onError: (e: Throwable) -> Unit
    ) {
        networkDataSource.getTeamTodos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = onSuccess, onError = onError)
            .addTo(compositeDisposable)
    }

    override fun getPersonalTodos(
        onSuccess: (BaseResponse<List<TodoItem>>) -> Unit,
        onError: (e: Throwable) -> Unit
    ) {
        networkDataSource.getPersonalTodos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = onSuccess, onError = onError)
            .addTo(compositeDisposable)
    }

    override fun createTeamTodo(
        title: String, description: String, assignee: String,
        onSuccess: (BaseResponse<TodoItem>) -> Unit,
        onError: (e: Throwable) -> Unit
    ) {
        val todoTeamRequest = TodoTeamRequest(title, description, assignee)

        networkDataSource.createTeamTodo(todoTeamRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = onSuccess, onError = onError)
            .addTo(compositeDisposable)
    }

    override fun createPersonalTodo(
        title: String, description: String,
        onSuccess: (BaseResponse<TodoItem>) -> Unit,
        onError: (e: Throwable) -> Unit
    ) {
        val todoPersonalRequest = TodoPersonalRequest(title, description)
        networkDataSource.createPersonalTodo(todoPersonalRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = onSuccess, onError = onError)
            .addTo(compositeDisposable)
    }

    override fun updateTeamTodoStatus(
        todoId: String, newStatus: TodoState,
        onSuccess: (BaseResponse<String>) -> Unit,
        onError: (e: Throwable) -> Unit
    ) {
        val todoStatusRequest = TodoStatusRequest(todoId, newStatus)

        networkDataSource.updateTeamTodoStatus(todoStatusRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = onSuccess, onError = onError)
            .addTo(compositeDisposable)
    }

    override fun updatePersonalTodoStatus(
        todoId: String, newStatus: TodoState,
        onSuccess: (BaseResponse<String>) -> Unit,
        onError: (e: Throwable) -> Unit
    ) {
        val todoStatusRequest = TodoStatusRequest(todoId, newStatus)
        networkDataSource.updatePersonalTodoStatus(todoStatusRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = onSuccess, onError = onError)
            .addTo(compositeDisposable)
    }

    override fun isTokenValid(): Boolean {
        val expiry = sharedPreferencesService.getExpireDate()
        return if (expiry != null && expiry.isNotEmpty()) {
            System.currentTimeMillis() / 1000 < formattedTime(expiry)
        } else {
            false
        }
    }

    override fun formattedTime(expiry: String) =
        SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }.parse(expiry)?.time?.div(1000) ?: 0

}
