package com.cheesecake.todo.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import com.cheesecake.todo.R
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.repository.todos.TodoRepositoryFactory
import com.cheesecake.todo.databinding.FragmentHomeBinding
import com.cheesecake.todo.ui.base.BaseFragment
import com.cheesecake.todo.ui.login.LoginFragment
import com.cheesecake.todo.ui.taskDetails.TaskDetailsFragment
import com.cheesecake.todo.ui.viewall.ViewAllTodoItemsFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeView, OnQueryTextListener {

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    private lateinit var presenter: HomePresenter
    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        addSearchCallBack()
    }

    private fun addSearchCallBack() {
        binding.searchBar.setOnQueryTextListener(this)
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
            val todosList = if ((homeList[0] as DataItem.TagItem).tag.title.contains("Personal")) {
                listOf(
                    DataItem.Header(
                        (homeList[0] as DataItem.TagItem).tag.todos,
                        (homeList[1] as DataItem.TagItem).tag.todos
                    ),
                    homeList[0],
                    homeList[1]
                )
            } else {
                listOf(
                    DataItem.Header(
                        (homeList[1] as DataItem.TagItem).tag.todos,
                        (homeList[0] as DataItem.TagItem).tag.todos
                    ),
                    homeList[1],
                    homeList[0]
                )
            }
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

    private fun loadDetailsFragment(todoItem: TodoItem, isPersonal: Boolean) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.fragment_container_activity,
                TaskDetailsFragment.newInstance(todoItem, isPersonal)
            )
            addToBackStack(null)
            commit()
        }
    }

    override fun navigateToLoginScreen() {
        loadLoginFragment()
    }

    private fun loadLoginFragment() {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_activity, LoginFragment())
            commit()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {

        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        val list = presenter.search(p0.toString())
        Log.d("onQueryTextChange: ", list.toString())
        if (list.isEmpty() || p0!!.isEmpty()) {
            binding.recyclerViewHome.adapter = homeAdapter
        } else {
            val adapter = TodoItemAdapter(::loadDetailsFragment)
            adapter.submitList(list)
            binding.recyclerViewHome.adapter = adapter
        }
        return false
    }


}