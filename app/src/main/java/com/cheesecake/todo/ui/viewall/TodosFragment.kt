package com.cheesecake.todo.ui.viewall

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.cheesecake.todo.R
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.repository.todos.TodoRepositoryFactory
import com.cheesecake.todo.databinding.FragmentViewAllTodoItemsBinding
import com.cheesecake.todo.ui.base.BaseFragment
import com.cheesecake.todo.ui.creation.TodoCreationFragment
import com.cheesecake.todo.ui.home.TodoItemAdapter
import com.cheesecake.todo.ui.login.LoginFragment
import com.cheesecake.todo.ui.taskDetails.TaskDetailsFragment


class TodosFragment : BaseFragment<FragmentViewAllTodoItemsBinding, TodosPresenter>(),
    TodosContract.IView {

    override val bindingInflater: (LayoutInflater) -> FragmentViewAllTodoItemsBinding =
        FragmentViewAllTodoItemsBinding::inflate

    override val presenter by lazy {
        val todoFactory = requireActivity().application as TodoRepositoryFactory
        TodosPresenter(
            todoFactory.createTodoRepository(),
            this,
            _isPersonalStatus
                ?: throw IllegalArgumentException("Cannot determine type of todos. _isPersonal cannot be null.")
        )
    }

    private lateinit var adapter: TodoItemAdapter
    private var _isPersonalStatus: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _isPersonalStatus = it.getBoolean(IS_PERSONAL_KEY)
        }
        presenter.requestAllTodos()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        addCallbacks()

    }

    private fun initView() {
        adapter = TodoItemAdapter(::loadDetailsFragment)
        binding.toggleButtonTodo.performClick()
        binding.recyclerViewAllTodos.adapter = adapter
    }

    private fun addCallbacks() {
        binding.apply {
            toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
                if (isChecked) {
                    when (checkedId) {
                        R.id.toggle_button_todo -> {
                            Log.d("TAG", "addCallbacks:todo selected ")
                            toggleSelected(0)
                        }
                        R.id.toggle_button_progress -> {
                            toggleSelected(1)
                        }
                        R.id.toggle_button_done -> {
                            toggleSelected(2)
                        }
                    }
                }
            }
            fabAddTodo.setOnClickListener {
                createTodo()
            }

        }
    }

    companion object {
        private const val IS_PERSONAL_KEY = "is_personal_key"

        fun newInstance(isPersonal: Boolean) = TodosFragment().apply {
            arguments = Bundle().apply {
                putBoolean(IS_PERSONAL_KEY, isPersonal)
            }
        }
    }

    override fun showTodos(todos: List<TodoItem>) {
        Log.e("showTodos: ", todos.toString())
        requireActivity().runOnUiThread {
            adapter.submitList(todos)
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


    override fun showError(message: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }

    override fun navigateToLoginScreen() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_activity, LoginFragment()).commit()
    }

    override fun toggleSelected(position: Int) {
        presenter.onToggleSelected(position)
    }

    private fun createTodo() {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.fragment_container_activity,
                TodoCreationFragment.newInstance(_isPersonalStatus!!)
            )
            addToBackStack(null)
            commit()
        }
    }
}