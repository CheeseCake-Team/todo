package com.cheesecake.todo.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.cheesecake.todo.R
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.repository.todos.TodoRepositoryFactory
import com.cheesecake.todo.databinding.FragmentHomeBinding
import com.cheesecake.todo.ui.base.BaseFragment
import com.cheesecake.todo.ui.taskDetails.TaskDetailsFragment
import com.cheesecake.todo.ui.viewall.ViewAllTodoItemsFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeView {

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate
    private lateinit var presenter: HomePresenter
    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }


    private fun setUp() {
        val todoFactory = requireActivity().application as TodoRepositoryFactory
        presenter = HomePresenter(todoFactory.createTodoRepository())
        presenter.attachView(this)
        presenter.initTodos()
        homeAdapter = HomeAdapter(::loadDetailsFragment, ::loadViewAllFragment)
        binding.recyclerViewHome.adapter = homeAdapter
    }

    override fun initHomeList(homeList: MutableList<DataItem>) {
        requireActivity().runOnUiThread {
            while (homeList.size < 2) {
                Log.e("while", "here")
            }
            Log.d("list", homeList[0].toString())
            Log.d("list", homeList[1].toString())
            val todosList = listOf(
                DataItem.Header(
                    (homeList[0] as DataItem.TagItem).tag.todos,
                    (homeList[1] as DataItem.TagItem).tag.todos
                ),
                homeList[0],
                homeList[1]
            )
            homeAdapter.submitList(todosList)
        }
    }

    override fun showError(message: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            Log.e("showError: ", message)
        }
    }

    private fun loadViewAllFragment(todoTitle: String) {
        val isPersonal = todoTitle.contains("Personal")
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.fragment_container_activity,
                ViewAllTodoItemsFragment.newInstance(isPersonal)
            )
            addToBackStack(null)
            commit()
        }
    }

    private fun loadDetailsFragment(todoItem: TodoItem) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_activity, TaskDetailsFragment.newInstance(todoItem))
            addToBackStack(null)
            commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}