package com.cheesecake.todo.data.repository.todos

import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.data.models.request.TodoPersonalRequest
import com.cheesecake.todo.data.models.request.TodoStatusRequest
import com.cheesecake.todo.data.models.request.TodoTeamRequest
import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.network.ResponseCallback
import com.cheesecake.todo.data.network.todos.TodoNetworkService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class TodoRepositoryImpl(
    private val networkDataSource: TodoNetworkService,
    private val sharedPreferencesService: SharedPreferencesService
) : TodoRepository {

    override fun getTeamTodos(todoCallback: TodoCallback) {
        networkDataSource
            .getTeamTodos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.isSuccess) {
                        todoCallback.onSuccessTeamTodo(response.value)
                    } else {
                        todoCallback.onError(response.message)
                    }
                },
                { error ->
                    todoCallback.onError(error.message ?: "Unknown error")
                }
            )
    }

    override fun getPersonalTodos(todoCallback: TodoCallback) {
        networkDataSource
            .getPersonalTodos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.isSuccess) {
                        todoCallback.onSuccessPersonalTodo(response.value)
                    } else {
                        todoCallback.onError(response.message)
                    }
                },
                { error ->
                    todoCallback.onError(error.message ?: "Unknown error")
                }
            )
    }

    override fun createTeamTodo(
        title: String, description: String, assignee: String, todoCallback: TodoCallback
    ) {
        val todoTeamRequest = TodoTeamRequest(title, description, assignee)
         object : ResponseCallback {
            override fun <T> onSuccess(response: BaseResponse<T>) {
                if (response.isSuccess) {
                    todoCallback.onSuccessTeamTodo()
                } else todoCallback.onError(response.message)
            }

            override fun onFail(error: String) {
                todoCallback.onError(error)
            }
        }
        networkDataSource
            .createTeamTodo(todoTeamRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.isSuccess) {
                        todoCallback.onSuccessTeamTodo()
                    } else {
                        todoCallback.onError(response.message)
                    }
                },
                { error ->
                    todoCallback.onError(error.message ?: "Unknown error")
                }
            )
    }

    override fun createPersonalTodo(
        title: String, description: String, todoCallback: TodoCallback
    ) {
        val todoPersonalRequest = TodoPersonalRequest(title, description)
        networkDataSource
            .createPersonalTodo(todoPersonalRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.isSuccess) {
                        todoCallback.onSuccessPersonalTodo()
                    } else {
                        todoCallback.onError(response.message)
                    }
                },
                { error ->
                    todoCallback.onError(error.message ?: "Unknown error")
                }
            )
    }

    override fun updateTeamTodoStatus(
        todoId: String, newStatus: TodoState, todoCallback: TodoCallback
    ) {
        val todoStatusRequest = TodoStatusRequest(todoId, newStatus)

        networkDataSource
            .updateTeamTodoStatus(todoStatusRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.isSuccess) {
                        todoCallback.onSuccessTeamTodo()
                    } else {
                        todoCallback.onError(response.message)
                    }
                },
                { error ->
                    todoCallback.onError(error.message ?: "Unknown error")
                }
            )
    }

    override fun updatePersonalTodoStatus(
        todoId: String, newStatus: TodoState, todoCallback: TodoCallback
    ) {
        val todoStatusRequest = TodoStatusRequest(todoId, newStatus)
        networkDataSource
            .updatePersonalTodoStatus(todoStatusRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.isSuccess) {
                        todoCallback.onSuccessPersonalTodo()
                    } else {
                        todoCallback.onError(response.message)
                    }
                },
                { error ->
                    todoCallback.onError(error.message ?: "Unknown error")
                }
            )
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
