package com.cheesecake.todo.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.databinding.ItemHomeHeaderBinding
import com.cheesecake.todo.databinding.ItemHomeRecyclerBinding
import com.cheesecake.todo.databinding.ItemTodoCardsBinding
import com.cheesecake.todo.ui.base.BaseItemViewHolder


private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_TODO_CARDS = 1
private const val ITEM_VIEW_TYPE_TODO_recycler = 2

class HomeAdapter(
    private var homeList: List<*>,
//    private val listener: () -> Unit
    private var context: Context

) : RecyclerView.Adapter<BaseItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder(
                ItemHomeHeaderBinding.inflate(
                    layoutInflater, parent, false
                )
            )
            ITEM_VIEW_TYPE_TODO_CARDS -> CardsViewHolder(
                ItemTodoCardsBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
            else -> TodoRecyclerViewHolder(
                ItemHomeRecyclerBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
        }
    }


    override fun onBindViewHolder(holder: BaseItemViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val item = homeList[0] as TodoItem
                //holder.bind(item)
            }
            is CardsViewHolder -> {
                val item = homeList[1] as TodoItem
                holder.bind(item)
            }
            is TodoRecyclerViewHolder -> {
                val item = homeList[position] as List<TodoItem>
                holder.bind(item)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ITEM_VIEW_TYPE_HEADER
            1 -> ITEM_VIEW_TYPE_TODO_CARDS
            else -> ITEM_VIEW_TYPE_TODO_recycler
        }
    }

    override fun getItemCount() = homeList.size

    inner class HeaderViewHolder(private val binding: ItemHomeHeaderBinding) :
        BaseItemViewHolder(binding) {

    }

    inner class CardsViewHolder(private val binding: ItemTodoCardsBinding) :
        BaseItemViewHolder(binding) {
        fun bind(todoItem: TodoItem) {
        }
    }

    inner class TodoRecyclerViewHolder(private val binding: ItemHomeRecyclerBinding) :
        BaseItemViewHolder(binding) {
        fun bind(todoItem: List<TodoItem>) {
            if (todoItem[0].assignee == null) {
                binding.textRecently.text = "Recently Personal"
            } else {
                binding.textRecently.text = "Recently Team"
            }
            binding.recyclerView.adapter = SearchTodosAdapter(todoItem)
            //binding.textViewAll.setOnClickListener { listener }
        }

    }


}