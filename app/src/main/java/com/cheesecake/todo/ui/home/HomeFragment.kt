package com.cheesecake.todo.ui.home

import ViewAllTodoItemsFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.cheesecake.todo.R
import com.cheesecake.todo.data.models.Tag
import com.cheesecake.todo.databinding.FragmentHomeBinding
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.ui.base.BaseFragment

class HomeFragment() : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todosList =
            listOf<TodoItem>(
                TodoItem(",", "", "", "", TodoState.TODO, ""),
                TodoItem(",", "", "", "",  TodoState.TODO, ""),
                TodoItem(",", "", "", "",  TodoState.TODO, ""),
                TodoItem(",", "", "", "",  TodoState.TODO, "")
            )
        val todosList0 =
            listOf<TodoItem>(
                TodoItem(",", "", "", "", TodoState.TODO, ""),
                TodoItem(",", "", "", "",  TodoState.TODO, ""),
                TodoItem(",", "", "", "",  TodoState.TODO, ""),
                TodoItem(",", "", "", "",  TodoState.TODO, "")

            )
        val list = listOf(
            DataItem.Header(10,10,20),
            DataItem.TagItem(Tag(0,"afdsafds", todosList)),
            DataItem.TagItem(Tag(1,"sadfsdafffff", todosList0))
        )
         val homeAdapter = HomeAdapter(
            object : TodoItemClickListener {
                override fun onTodoItemClick(todoItem: TodoItem) {

                }
            })
        homeAdapter.submitList(list)
        binding.recyclerViewHome.adapter = homeAdapter
    }


    private fun loadViewAllFragment() {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_activity, ViewAllTodoItemsFragment())
            addToBackStack(null)
            commit()
        }
    }

}