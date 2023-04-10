package com.cheesecake.todo.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cheesecake.todo.R
import com.cheesecake.todo.databinding.ItemCardViewTodoBinding
import com.cheesecake.todo.data.models.TodoItem

class SearchTodosAdapter(private var todos: List<TodoItem>) :
    RecyclerView.Adapter<SearchTodosAdapter.TodoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_view_todo, parent, false)
        return TodoViewHolder(view)
    }


    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todos[position])
    }

    override fun getItemCount() = todos.size

    fun updateTodos(newTodos: List<TodoItem>) {
        val diffResult = DiffUtil.calculateDiff(TodoDiffUtil(todos, newTodos))
        todos = newTodos
        diffResult.dispatchUpdatesTo(this)
    }


    inner class TodoViewHolder(itemCard: View) :
        RecyclerView.ViewHolder(itemCard) {
        private val binding = ItemCardViewTodoBinding.bind(itemCard)
        fun bind(todoItem: TodoItem) {
            binding.textViewTeamTodoTitle.text = todoItem.title
            binding.textViewTeamTodoDescription.text = todoItem.description
            binding.textViewTeamTodoAssignee.text = todoItem.assignee

            val creationDate = todoItem.creationTime.substring(0, 10)
            val creationTime = todoItem.creationTime.substring(11, 23)

            binding.textViewTeamTodoCreationDate.text = creationDate

        }

    }


    inner class TodoDiffUtil(
        private val oldTodoList: List<TodoItem>,
        private val newTodoList: List<TodoItem>
    ) :
        DiffUtil.Callback() {

        override fun getOldListSize() = oldTodoList.size

        override fun getNewListSize() = newTodoList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (
                    oldTodoList[oldItemPosition].id == newTodoList[newItemPosition].id
                            && oldTodoList[oldItemPosition].title == newTodoList[newItemPosition].title
                    )
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (
                    oldTodoList[oldItemPosition].status == newTodoList[newItemPosition].status
                    )
        }

    }
}