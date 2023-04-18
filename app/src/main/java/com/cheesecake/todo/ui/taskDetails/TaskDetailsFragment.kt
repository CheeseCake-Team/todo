package com.cheesecake.todo.ui.taskDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.databinding.FragmentTaskDetailsBinding
import com.cheesecake.todo.ui.base.BaseFragment
import com.cheesecake.todo.ui.viewall.ViewAllTodoItemsFragment

private const val TODO_KEY = "todo"
private const val IS_PERSONAL_KEY = "is_personal_key"

class TaskDetailsFragment : BaseFragment<FragmentTaskDetailsBinding>() {
    private var toDo: TodoItem? = null
    private var isPersonal: Boolean? = null
    override val bindingInflater: (LayoutInflater) -> FragmentTaskDetailsBinding =
        FragmentTaskDetailsBinding::inflate

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
        binding.textViewTaskDate.text = toDo?.creationTime.toString()
        binding.textViewTaskTime.text = toDo?.creationTime.toString()
        binding.textViewTaskContent.text = toDo?.description.toString()
        binding.textViewUserName.text = toDo?.assignee.toString()
        if (isPersonal!!){
            binding.textViewUserName.visibility = View.INVISIBLE
            binding.imageViewUserIcon.visibility = View.INVISIBLE
        }
        else{
            binding.textViewUserName.visibility = View.VISIBLE
            binding.imageViewUserIcon.visibility = View.VISIBLE
        }
    }


    companion object {
        fun newInstance(todo: TodoItem,isPersonal: Boolean) = TaskDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(TODO_KEY, todo)
                putBoolean(IS_PERSONAL_KEY,isPersonal)
            }
        }
    }
}
