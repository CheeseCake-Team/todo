package com.cheesecake.todo.ui.taskDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.cheesecake.todo.R
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.data.repository.todos.TodoRepositoryFactory
import com.cheesecake.todo.databinding.FragmentTaskDetailsBinding
import com.cheesecake.todo.ui.base.BaseFragment

private const val TODO_KEY = "todo"
private const val IS_PERSONAL_KEY = "is_personal_key"

class TaskDetailsFragment : BaseFragment<FragmentTaskDetailsBinding, TaskDetailsPresenter>(),
    TaskDetailsView {


    override val bindingInflater: (LayoutInflater) -> FragmentTaskDetailsBinding =
        FragmentTaskDetailsBinding::inflate

    override val presenter by lazy {
        val todoFactory = requireActivity().application as TodoRepositoryFactory
        TaskDetailsPresenter(
            todoFactory.createTodoRepository(),
            this,
        )
    }

    private var toDo: TodoItem? = null
    private var isPersonal: Boolean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            toDo = it.getParcelable(TODO_KEY)
            isPersonal = it.getBoolean(IS_PERSONAL_KEY)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewTaskName.text = toDo?.title.toString()
        binding.textViewTaskDate.text = toDo?.creationTime.toString().substringBefore("T")
        binding.textViewTaskContent.text = toDo?.description.toString()
        binding.textViewUserName.text = toDo?.assignee.toString()
        if (isPersonal!!) {
            binding.textViewUserName.visibility = View.INVISIBLE
            binding.imageViewUserIcon.visibility = View.INVISIBLE
        } else {
            binding.textViewUserName.visibility = View.VISIBLE
            binding.imageViewUserIcon.visibility = View.VISIBLE
        }
        binding.apply {
            toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
                if (isChecked) {
                    when (checkedId) {
                        R.id.task_details_toggle_button_todo -> updateState(TodoState.TODO)
                        R.id.task_details_toggle_button_progress -> updateState(TodoState.IN_PROGRESS)
                        else -> updateState(TodoState.DONE)
                    }
                }
            }
            when(toDo?.status){
                TodoState.TODO -> taskDetailsToggleButtonTodo.performClick()
                TodoState.IN_PROGRESS -> taskDetailsToggleButtonProgress.performClick()
                else -> taskDetailsToggleButtonDone.performClick()
            }
        }
    }


    companion object {
        fun newInstance(todo: TodoItem, isPersonal: Boolean) = TaskDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(TODO_KEY, todo)
                putBoolean(IS_PERSONAL_KEY, isPersonal)
            }
        }
    }

    override fun updateState(todoState: TodoState) =
        presenter.updateState(toDo!!, isPersonal!!, todoState)


    override fun showError(errorMessage: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }


}
