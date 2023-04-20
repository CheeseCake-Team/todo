package com.cheesecake.todo.ui.creation

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.cheesecake.todo.R
import com.cheesecake.todo.data.repository.todos.TodoRepositoryFactory
import com.cheesecake.todo.databinding.FragmentCreateToDoBinding
import com.cheesecake.todo.ui.base.BaseFragment
import kotlin.properties.Delegates

class TodoCreationFragment : BaseFragment<FragmentCreateToDoBinding, TodoCreationPresenter>(),
    TodoCreationView {
    override val bindingInflater: (LayoutInflater) -> FragmentCreateToDoBinding =
        FragmentCreateToDoBinding::inflate

    override val presenter by lazy {
        val todoFactory = requireActivity().application as TodoRepositoryFactory
        TodoCreationPresenter(todoFactory.createTodoRepository(), this)
    }

    private lateinit var title: String
    private lateinit var description: String
    private var isPersonal by Delegates.notNull<Boolean>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            isPersonal = it.getBoolean(IS_PERSONAL_KEY)
        }
        onScreenInit()
        initCallbacks()
    }

    private fun onScreenInit() {
        with(binding) {
            if (isPersonal) {
                chipPersonalTodo.apply {
                    isChecked = true
                    isEnabled = false
                }
            } else {
                chipTeamTodo.apply {
                    isChecked = true
                    isEnabled = false
                }
                assigned.visibility = View.VISIBLE
            }
        }
    }

    private fun initCallbacks() {
        binding.apply {
            chipPersonalTodo.setOnClickListener {
                chipTeamTodo.isChecked = false
                assigned.visibility = View.INVISIBLE
                isPersonal = true
                chipPersonalTodo.isEnabled = false
                chipTeamTodo.isEnabled = true

            }
            chipTeamTodo.setOnClickListener {
                chipPersonalTodo.isChecked = false
                assigned.visibility = View.VISIBLE
                isPersonal = false
                chipPersonalTodo.isEnabled = true
                chipTeamTodo.isEnabled = false
            }

            buttonCreate.setOnClickListener {
                title = binding.editTextTitle.text.toString()
                description = binding.editTextDescription.text.toString()
                if (checkEmptyField(title, description)) {
                    makeRequest()
                }
            }
        }
    }


    private fun makeRequest() {
        when (isPersonal) {
            isPersonal -> {
                presenter.toDoForPersonal(
                    title, description, "person",
                )
            }
            else -> {
                presenter.toDoForTeam(
                    title, description, "team",
                )
            }
        }
    }

    private fun checkEmptyField(title: String, description: String): Boolean {
        when {
            title.trim().isEmpty() -> {
                Toast.makeText(
                    requireContext(), "Please fill title field", Toast.LENGTH_SHORT
                ).show()
                return false
            }
            description.trim().isEmpty() -> {
                Toast.makeText(
                    requireContext(), "Please fill description field", Toast.LENGTH_SHORT
                ).show()
                return false
            }
        }
        return true
    }


    override fun showError(message: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }

    override fun showDialog() {
        requireActivity().runOnUiThread {

            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.confirmationdialog)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.setCancelable(false)
            dialog.window?.attributes?.windowAnimations = R.style.animationDialog

            val cancel = dialog.findViewById<TextView>(R.id.text_view_cancel_text_button)

            cancel.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    companion object {
        private const val IS_PERSONAL_KEY = "is_personal_key"
        fun newInstance(isPersonal: Boolean) = TodoCreationFragment().apply {
            arguments = Bundle().apply {
                putBoolean(IS_PERSONAL_KEY, isPersonal)
            }
        }
    }

}
