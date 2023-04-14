package com.cheesecake.todo.ui.viewall

import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

private const val END_POINT_TEAM = "\"https://team-todo-62dmq.ondigitalocean.app/todo/team\""
private const val END_POINT_PERSONAL =
    "\"https://team-todo-62dmq.ondigitalocean.app/todo/personal\""

class ViewAllPresenter(private val preferencesService: SharedPreferencesService) :
    ViewAllContract.IPresenter {

    private val _client = OkHttpClient()
    private var _view: ViewAllContract.IView? = null
    private val token = preferencesService.getToken()

    override fun attachView(view: ViewAllContract.IView) {
        this._view = view
    }

    override fun detachView() {
        this._view = null
    }

    override fun getTodos(isPersonal: Boolean){
        val request = when (isPersonal) {
            true -> requestAllTodos(END_POINT_PERSONAL)
            else -> requestAllTodos(END_POINT_TEAM)
        }

        _client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                _view!!.showError(e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    val todoJsonArray = JSONObject(response.body!!.string()).getJSONArray("value")
                    val todos = jsonArrayParser(todoJsonArray)
                    _view!!.showTodos(todos)
                }else{
                    _view!!.navigateToLoginScreen()
                }
            }
        })
    }

    private fun requestAllTodos(endPoint: String) = Request.Builder()
        .url(endPoint)
        .addHeader("Authorization", "Bearer $token")
        .build()


    private fun jsonArrayParser(todoJsonArray: JSONArray): List<TodoItem> {
        val todos = mutableListOf<TodoItem>()
        for(item in 0 until todoJsonArray.length()){
            val current = todoJsonArray.getJSONObject(item)
            todos.add(
                TodoItem(current.getString("id"),
                    current.getString("title"),
                    current.getString("description"),
                    current.getString("assignee"),
                    current.getString("status").toInt(),
                    current.getString("creationTime"))
            )
        }
        return todos
    }
}