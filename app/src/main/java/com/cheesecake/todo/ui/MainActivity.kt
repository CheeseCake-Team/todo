package com.cheesecake.todo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.cheesecake.todo.databinding.ActivityMainBinding
import com.cheesecake.todo.network.MyHttpClient
import com.cheesecake.todo.network.NetworkService
import com.cheesecake.todo.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val client = MyHttpClient()
        val service = NetworkService.getInstance()
        var token: String? =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6ImI3ZTE2NWZlLWMxZGEtNDc4ZC1iZjg2LTRlMDVkMjE3Zjg5NyIsInRlYW1JZCI6IjUwNDczNjllLTVkMTQtNGUyZi04ZmUxLWY2MjA4N2VjMjhkOCIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjoxNjgxMTE2MjgxfQ.h5zpqSAI2rWjKghFI4vdZAqXtwt8NwCSRTlO2VoaYIc"

        Thread {
            service.login("shehab2", "123456789") { pair, error ->
                if (error != null) {
                    Log.d("TAG", "error in login: $error")
                } else {
                    Log.d("TAG", "onCreate: $pair")
                    token = pair?.first
                }
            }
        }.start()

        Thread {
            service.getTodos(false, token!!) { todos, error ->
                if (error != null) {
                    println("Error getting todos: $error")
                } else {
                    todos?.forEach {
                        Log.d("TAG", "onCreate: ${it.title}")
                    }
                }
            }
        }.start()


    }

}