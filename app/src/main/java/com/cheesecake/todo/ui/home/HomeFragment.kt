package com.cheesecake.todo.ui.home

import ViewAllTodoItemsFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView

import com.cheesecake.todo.R
import com.cheesecake.todo.data.models.Tag
import com.cheesecake.todo.databinding.FragmentHomeBinding
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.ui.base.BaseFragment


class HomeFragment() : BaseFragment<FragmentHomeBinding>(), SearchView.OnQueryTextListener {
    private var searchAdapter = TodoItemAdapter(object : TodoItemClickListener {
        override fun onTodoItemClick(todoItem: TodoItem) {

        }
    })
    private val homeAdapter = HomeAdapter(object : TodoItemClickListener {
        override fun onTodoItemClick(todoItem: TodoItem) {

        }
    })

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        addCallbacks()


    }

    private fun setUp() {
        homeAdapter.submitList(list)
        addSearchListener()
    }

    private fun addSearchListener() {
        binding.searchBar.setOnQueryTextListener(this)
    }

    private fun searchByQueryAndSetDataInAdapter(query: String?) {
        query?.let {
            binding.apply {
                setDataOnAdapter(it)
            }
        }
    }

    private fun setDataOnAdapter(query: String) {
        if (query.isNotEmpty()) {
            val resultOfSearch = searchByTitleOrDescription(query)
            searchAdapter.submitList(resultOfSearch)
            binding.recyclerViewHome.adapter = searchAdapter
        } else {

            binding.recyclerViewHome.adapter = homeAdapter
        }

    }

    private fun addCallbacks() {
        binding.apply {
            recyclerViewHome.adapter = homeAdapter
        }
    }


    private fun loadViewAllFragment() {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_activity, ViewAllTodoItemsFragment())
            addToBackStack(null)
            commit()
        }
    }


    private fun searchByTitleOrDescription(searchWord: String): List<TodoItem> {
        return list2.filter {
                it.title.lowercase().contains(searchWord.lowercase()) || it.description.lowercase()
                    .contains(searchWord.lowercase())
            }
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { searchByQueryAndSetDataInAdapter(it) }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { searchByQueryAndSetDataInAdapter(it) }
        return true
    }

    private var list2 = listOf<TodoItem>(
        TodoItem(",", "ll", "", "", TodoState.TODO, ""),
        TodoItem(",", "", "", "", TodoState.TODO, ""),
        TodoItem(",", "", "mm", "", TodoState.TODO, ""),
        TodoItem(",", "", "", "", TodoState.TODO, "")
    )

    val todosList = listOf<TodoItem>(
        TodoItem(",", "", "", "", TodoState.TODO, ""),
        TodoItem(",", "", "", "", TodoState.TODO, ""),
        TodoItem(",", "", "", "", TodoState.TODO, ""),
        TodoItem(",", "", "", "", TodoState.TODO, "")
    )
    val todosList0 = listOf<TodoItem>(
        TodoItem(",", "", "", "", TodoState.TODO, ""),
        TodoItem(",", "", "", "", TodoState.TODO, ""),
        TodoItem(",", "", "", "", TodoState.TODO, ""),
        TodoItem(",", "", "", "", TodoState.TODO, "")

    )
    val list = listOf(
        DataItem.Header(10, 10, 20),
        DataItem.TagItem(Tag(0, "afdsafds", todosList)),
        DataItem.TagItem(Tag(1, "sadfsdafffff", todosList0))
    )


}