package com.cheesecake.todo.ui.viewall

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cheesecake.todo.R
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.databinding.ItemCardViewTodoBinding
import com.cheesecake.todo.databinding.ViewAllSegmentedButtonsBinding


class ViewAllAdapter (val todoItems: List<TodoItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        companion object {
        private const val ITEM_VIEW_TYPE_SEGMENTED = 0
        private const val ITEM_VIEW_TYPE_TODO_CARDS = 1
    }




    inner class SegmentedButtonGroupViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ViewAllSegmentedButtonsBinding.bind(itemView)
        private fun bind(){

        }
    }

    inner class TodoViewHolder(itemCard: View) :
        RecyclerView.ViewHolder(itemCard) {
        private val binding = ItemCardViewTodoBinding.bind(itemCard)
        fun bind(todoItem: TodoItem) {
            binding.apply {
                textViewTeamTodoTitle.text = todoItem.title
                textViewTeamTodoDescription.text = todoItem.description
                textViewTeamTodoAssignee.text = todoItem.assignee
                textViewTeamTodoCreationDate.text = todoItem.creationTime
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


}