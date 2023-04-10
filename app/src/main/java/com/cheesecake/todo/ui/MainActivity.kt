package com.cheesecake.todo.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.cheesecake.todo.R
import com.cheesecake.todo.databinding.ActivityMainBinding
import com.cheesecake.todo.ui.base.BaseActivity
import com.cheesecake.todo.ui.base.BaseFragment
import com.cheesecake.todo.ui.signup.SignUpFragment

import androidx.appcompat.app.AppCompatActivity
import com.cheesecake.todo.databinding.FragmentHomeBinding
import com.cheesecake.todo.models.TodoItem
import com.cheesecake.todo.ui.home.HomeAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
        binding.recyclerViewHome.adapter = HomeAdapter(todosList,this)

    override fun onStart() {
        super.onStart()
        initializeHomeScreen()
    }

    private fun loadFragmentIntoContainer(baseFragment: BaseFragment<*>) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_activity, baseFragment).commit()
    }

    }

//    private fun loadViewAllFragment() {
//        this.supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fragment_container, ViewAllFragment())
//            addToBackStack(null)
//            commit()
//        }
//    }

    private fun initializeHomeScreen() {
        binding.bottomNavigationBar.visibility = View.GONE
        loadFragmentIntoContainer(SignUpFragment())
    }
}