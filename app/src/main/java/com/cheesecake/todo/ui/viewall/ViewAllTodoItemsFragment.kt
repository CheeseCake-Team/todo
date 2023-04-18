package com.cheesecake.todo.ui.viewall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cheesecake.todo.R
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.repository.todos.TodoRepositoryFactory
import com.cheesecake.todo.databinding.FragmentViewAllTodoItemsBinding
import com.cheesecake.todo.ui.base.BaseFragment
import com.cheesecake.todo.ui.login.LoginFragment


class ViewAllTodoItemsFragment : BaseFragment<FragmentViewAllTodoItemsBinding>(),
    ViewAllContract.IView {

    override val bindingInflater: (LayoutInflater) -> FragmentViewAllTodoItemsBinding =
        FragmentViewAllTodoItemsBinding::inflate


    private lateinit var presenter: ViewAllContract.IPresenter
    private lateinit var adapter: ViewAllAdapter
    private var _isPersonalStatus: Boolean? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _isPersonalStatus = it.getBoolean(IS_PERSONAL_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val todoFactory = requireActivity().application as TodoRepositoryFactory
        presenter = ViewAllPresenter(todoFactory.createTodoRepository())
        presenter.attachView(this, _isPersonalStatus)
        presenter.requestAllTodos()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    companion object {
        private const val IS_PERSONAL_KEY = "is_personal_key"

        fun newInstance(isPersonal: Boolean) =
            ViewAllTodoItemsFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_PERSONAL_KEY, isPersonal)
                }
            }
    }

    override fun showTodos(todos: List<TodoItem>) {
        requireActivity().runOnUiThread {
            adapter = ViewAllAdapter(todos, ::toggleSelected)
            binding.recyclerViewAllTodos.adapter = adapter
        }
    }


    override fun showError(message: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }

    override fun navigateToLoginScreen() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_activity, LoginFragment())
            .commit()
    }

    override fun toggleSelected(position: Int) {
        presenter.onToggleSelected(position)
    }


}