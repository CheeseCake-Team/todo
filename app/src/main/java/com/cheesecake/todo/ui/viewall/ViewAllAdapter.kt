package com.cheesecake.todo.ui.viewall

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.databinding.FragmentViewAllTodoItemsBinding
import com.cheesecake.todo.databinding.ItemCardViewTodoBinding
import com.cheesecake.todo.databinding.ItemHomeRecyclerBinding
import com.cheesecake.todo.databinding.NestedViewAllRecyclerBinding
import com.cheesecake.todo.databinding.ViewAllSegmentedButtonsBinding
import com.cheesecake.todo.ui.base.BaseItemViewHolder
import com.cheesecake.todo.ui.base.TodoDiffUtil
import com.cheesecake.todo.ui.home.SearchTodosAdapter

private const val ITEM_VIEW_TYPE_SEGMENTED = 0
private const val ITEM_VIEW_TYPE_TODO_CARDS = 1

class ViewAllAdapter(private var todoItems: List<TodoItem>,private val listener:(Int) -> Unit) :
    RecyclerView.Adapter<BaseItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_VIEW_TYPE_SEGMENTED -> ToggleButtonViewHolder(
                ViewAllSegmentedButtonsBinding.inflate(
                    layoutInflater, parent, false
                ),listener
            )
            else -> TodoViewHolder(
                NestedViewAllRecyclerBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return todoItems.size + 1
    }

    override fun onBindViewHolder(holder: BaseItemViewHolder, position: Int) {
        when (holder) {
            is ToggleButtonViewHolder -> {
                holder.bind()
            }

            is TodoViewHolder -> {
                holder.bind(todoItems)
            }

            else -> throw IllegalStateException("Unknown view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            ITEM_VIEW_TYPE_SEGMENTED
        } else {
            ITEM_VIEW_TYPE_TODO_CARDS
        }
    }

    fun updateTodos(newTodos: List<TodoItem>) {
        val diffResult = DiffUtil.calculateDiff(TodoDiffUtil(todoItems, newTodos))
        todoItems = newTodos
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ToggleButtonViewHolder(private val binding: ViewAllSegmentedButtonsBinding,private val listener:(Int) -> Unit) : BaseItemViewHolder(binding) {
        fun bind(){
            binding.toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, _ ->
                listener(checkedId)
            }
        }
    }

    inner class TodoViewHolder(private val binding: NestedViewAllRecyclerBinding) :
        BaseItemViewHolder(binding) {
        fun bind(todosList: List<TodoItem>) {
            binding.nestedRecyclerViewAllTodos.adapter = SearchTodosAdapter(todosList)
        }
    }
}
