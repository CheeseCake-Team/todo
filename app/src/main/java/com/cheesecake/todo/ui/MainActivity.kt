package com.cheesecake.todo.ui


import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import com.cheesecake.todo.R
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.databinding.ActivityMainBinding
import com.cheesecake.todo.ui.base.BaseActivity
import com.cheesecake.todo.ui.base.BaseFragment
import com.cheesecake.todo.ui.signup.SignUpFragment

import com.cheesecake.todo.ui.createtodo.ToDoFragment
import com.cheesecake.todo.ui.login.LoginFragment
import com.cheesecake.todo.ui.viewall.ViewAllTodoItemsFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeHomeScreen()
    }


    private fun loadFragmentIntoContainer(baseFragment: BaseFragment<*>) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_activity, baseFragment).commit()
    }


    private fun initializeHomeScreen() {
       binding.appbar.visibility = View.GONE
       loadFragmentIntoContainer(LoginFragment())
    }
}