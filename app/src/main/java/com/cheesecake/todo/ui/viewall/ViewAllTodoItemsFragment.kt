package com.cheesecake.todo.ui.viewall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.cheesecake.todo.databinding.FragmentViewAllTodoItemsBinding
import com.cheesecake.todo.models.TodoItem
import com.cheesecake.todo.ui.base.BaseFragment

class ViewAllTodoItemsFragment : BaseFragment<FragmentViewAllTodoItemsBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentViewAllTodoItemsBinding =
        FragmentViewAllTodoItemsBinding::inflate

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewAllTodos.adapter = VIewAllTodoAdapter(todosList[2] as List<TodoItem>)
    }



}