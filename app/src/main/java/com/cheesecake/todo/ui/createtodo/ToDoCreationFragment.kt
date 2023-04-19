package com.cheesecake.todo.ui.createtodo

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cheesecake.todo.R
import com.cheesecake.todo.data.repository.todos.TodoRepositoryFactory
import com.cheesecake.todo.databinding.FragmentCreateToDoBinding
import com.cheesecake.todo.ui.base.BaseFragment
import com.cheesecake.todo.utils.makeGone
import com.cheesecake.todo.utils.makeVisible
import kotlin.properties.Delegates

private const val IS_PERSONAL_KEY = "is_personal_key"

class ToDoCreationFragment : BaseFragment<FragmentCreateToDoBinding, TodoCreationPresenter>(),
    ToDoCreationView {
    override val bindingInflater: (LayoutInflater) -> FragmentCreateToDoBinding =
        FragmentCreateToDoBinding::inflate

    override val presenter by lazy {
        val todoFactory = requireActivity().application as TodoRepositoryFactory
        TodoCreationPresenter(todoFactory.createTodoRepository(), this)
    }

    private lateinit var title: String
    private lateinit var description: String
    private var person by Delegates.notNull<Boolean>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            person = it.getBoolean(IS_PERSONAL_KEY)
        }
        checkPersonalOrTeam()
        hasBackButtonOrNot()
        setActionBarTitle()
        createToDo()
        lol()

    }

    private fun getData() {
        title = binding.editTextTitle.text.toString()
        description = binding.editTextDescription.text.toString()
    }


    private fun createToDo() {
        binding.create.setOnClickListener {
            getData()
            if (checkEmptyField(title, description)) {
                makeRequest()
            }
        }
    }

    private fun makeRequest() {
        when (person) {
            person -> {
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

    private fun hasBackButtonOrNot() {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setActionBarTitle() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.create_task)
    }

    private fun checkEmptyField(title: String, description: String): Boolean {
        when {
            title.trim().isEmpty() -> {
                Toast.makeText(requireContext(), "Please fill title field", Toast.LENGTH_SHORT)
                    .show()
                return false
            }

            description.trim().isEmpty() -> {
                Toast.makeText(
                    requireContext(),
                    "Please fill description field",
                    Toast.LENGTH_SHORT
                )
                    .show()
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
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
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

    private fun lol() {
        when (person) {
            true -> {
                checkChip(
                    personal = true,
                    team = false,
                    colorPersonal = R.color.blue,
                    colorTeam = R.color.white,
                )
                binding.assigned.visibility = View.GONE
            }
            else -> {
                checkChip(
                    personal = false,
                    team = true,
                    colorPersonal = R.color.white,
                    colorTeam = R.color.blue,
                )
                binding.assigned.visibility = View.VISIBLE
            }
        }

    }

    private fun checkChip(
        personal: Boolean, team: Boolean,
        colorPersonal: Int, colorTeam: Int
    ) {
        binding.apply {
            chipPersonalTodo.apply {
                isCheckable = personal
                setChipBackgroundColorResource(colorPersonal)

            }
            chipTeamTodo.apply {
                isCheckable = team
                setChipBackgroundColorResource(colorTeam)
            }

        }
    }

    private fun checkPersonalOrTeam(): Boolean {

        binding.apply {
            chipPersonalTodo.setOnClickListener {
                person = when {
                    binding.chipPersonalTodo.isCheckable -> {
                        checkChip(
                            personal = false,
                            team = true,
                            colorPersonal = R.color.white,
                            colorTeam = R.color.blue,
                        )
                        assigned.makeVisible()

                        false
                    }

                    !binding.chipPersonalTodo.isCheckable -> {
                        checkChip(
                            personal = true,
                            team = false,
                            colorPersonal = R.color.blue,
                            colorTeam = R.color.white,
                        )
                        assigned.makeGone()

                        true
                    }
                    else -> false
                }
            }
            chipTeamTodo.setOnClickListener {
                person = when {
                    binding.chipTeamTodo.isCheckable -> {
                        checkChip(
                            personal = true,
                            team = false,
                            colorPersonal = R.color.blue,
                            colorTeam = R.color.white,
                        )
                        assigned.visibility = View.GONE

                        true
                    }
                    !binding.chipTeamTodo.isCheckable -> {
                        checkChip(
                            personal = false,
                            team = true,
                            colorPersonal = R.color.white,
                            colorTeam = R.color.blue,
                        )
                        assigned.visibility = View.VISIBLE

                        false
                    }
                    else -> false
                }
            }
        }
        return person
    }

    companion object {

        fun newInstance(isPersonal: Boolean) =
            ToDoCreationFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_PERSONAL_KEY, isPersonal)
                }
            }
    }

}