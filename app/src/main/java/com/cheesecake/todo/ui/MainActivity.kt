package com.cheesecake.todo.ui


import android.os.Bundle
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

        binding.recyclerViewHome.adapter = HomeAdapter(todosList,this)



    }

//    private fun loadViewAllFragment() {
//        this.supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fragment_container, ViewAllFragment())
//            addToBackStack(null)
//            commit()
//        }
//    }

}