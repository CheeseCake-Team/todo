package com.cheesecake.todo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.databinding.ItemTodoBinding

class TodoItemAdapter(private val listener: (item: TodoItem) -> Unit) :
    ListAdapter<TodoItem, TodoItemAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todoItem = getItem(position)
        holder.bind(todoItem, listener)
    }

    class TodoViewHolder(private val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(todoItem: TodoItem, listener: (item: TodoItem) -> Unit) {
            binding.textViewTeamTodoTitle.text = todoItem.title
            binding.textViewTeamTodoDescription.text = todoItem.description
            binding.textViewTeamTodoAssignee.text = todoItem.assignee
            binding.textViewTeamTodoCreationDate.text = todoItem.creationTime
            binding.cardViewLayoutId.setOnClickListener { listener(todoItem) }
        }
    }
}

class TodoDiffCallback : DiffUtil.ItemCallback<TodoItem>() {
    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem == newItem
    }
}

