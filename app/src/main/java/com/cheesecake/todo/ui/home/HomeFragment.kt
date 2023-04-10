package com.cheesecake.todo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.cheesecake.todo.databinding.FragmentHomeBinding
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.ui.base.BaseFragment

class HomeFragment() : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todosList =
            listOf<Any>(
                TodoItem(",", "", "", "", 3, ""),
                TodoItem(",", "", "", "", 3, ""),
                listOf<TodoItem>(
                    TodoItem(",", "", "", "", 3, ""),
                    TodoItem(",", "", "", "", 3, "")
                )
            )
        //binding.recyclerViewHome.adapter = HomeAdapter(todosList,::loadViewAllFragment)
    }

//    private fun loadViewAllFragment() {
//        requireActivity().supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fragment_container, ViewAllFragment())
//            addToBackStack(null)
//            commit()
//        }
//    }

}