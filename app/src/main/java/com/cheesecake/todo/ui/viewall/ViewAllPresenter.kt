package com.cheesecake.todo.ui.viewall

import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.data.repository.todos.TodoCallback
import com.cheesecake.todo.data.repository.todos.TodoRepository
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ViewAllPresenter(private val todoRepository: TodoRepository) :
    ViewAllContract.IPresenter,TodoCallback {

    private var _view: ViewAllContract.IView? = null

    override fun attachView(view: ViewAllContract.IView) {
        this._view = view
    }
    override fun detachView() {
        this._view = null
    }

    override fun getTodos(isPersonal: Boolean){
        todoRepository.getTodos(isPersonal,this)
    }

    override fun onSuccess(todos: List<TodoItem>?) {
        _view!!.showTodos(todos!!)
    }

    override fun onError(error: String) {
        _view!!.showError(error)
    }
}