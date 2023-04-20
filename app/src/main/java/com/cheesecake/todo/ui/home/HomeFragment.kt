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
import com.cheesecake.todo.ui.taskDetails.TaskDetailsFragment
import com.cheesecake.todo.ui.viewall.TodosFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, HomePresenter>(), HomeView,
    OnQueryTextListener {

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    override val presenter by lazy {
        val todoFactory = requireActivity().application as TodoRepositoryFactory
        val todoRepository = todoFactory.createTodoRepository()
        HomePresenter(todoRepository, this)
    }

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
        presenter.initTodos()
        homeAdapter = HomeAdapter(::loadDetailsFragment, ::loadViewAllFragment)
        binding.recyclerViewHome.adapter = homeAdapter
    }

    override fun initHomeList(homeList: MutableList<DataItem>) {
        requireActivity().runOnUiThread {
            val todosList = listOf(
                    DataItem.Header(
                        (homeList[0] as DataItem.TagItem).tag.todos,
                        (homeList[1] as DataItem.TagItem).tag.todos,
                    ), homeList[0], homeList[1]
                ).sortedBy { it.rank }
            Log.d("initHomeList: ", todosList.toString())
            Log.d("initHomeList: ", (homeList[0] as DataItem.TagItem).tag.toString())
            Log.d("initHomeList: ", (homeList[1] as DataItem.TagItem).tag.toString())
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
                R.id.fragment_container_activity, TodosFragment.newInstance(isPersonal)
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

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(text: String?): Boolean {
        val list = presenter.search(text.toString())
        Log.d("onQueryTextChange: ", list.toString())
        if (list.isEmpty() || text!!.isEmpty()) {
            binding.recyclerViewHome.adapter = homeAdapter
        } else {
            val adapter = TodoItemAdapter(::loadDetailsFragment)
            adapter.submitList(list)
            binding.recyclerViewHome.adapter = adapter
        }
        return false
    }

}