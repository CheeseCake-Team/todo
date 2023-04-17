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

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction().add(R.id.fragment_container,
            ToDoFragment())
            .commit()
        setContentView(binding.root)
        val todosList =
            listOf(
                TodoItem(",rfwea", "far", "arf", "afr", 3, "far"),
                TodoItem(",rfwea", "far", "arf", "afr", 3, "far"),
                listOf<TodoItem>(
                    TodoItem(",rfwea", "far", "arf", null, 3, "far"),
                    TodoItem(",rfwea", "far", "arf", null, 3, "far"),
                ), listOf<TodoItem>(
                    TodoItem(",rfwea", "far", "arf", "afr", 3, "far"),
                    TodoItem(",rfwea", "far", "arf", "afr", 3, "far"),
                )
            )
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onStart() {
        super.onStart()
        initializeHomeScreen()
    }

    private fun loadFragmentIntoContainer(baseFragment: BaseFragment<*>) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_activity, baseFragment).commit()
    }


    private fun initializeHomeScreen() {
//        binding.bottomNavigationBar.visibility = View.GONE
//        loadFragmentIntoContainer(SignUpFragment())
    }
}