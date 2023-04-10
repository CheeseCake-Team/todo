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
            binding.textViewTeamTodoTitle.text = todoItem.title
            binding.textViewTeamTodoDescription.text = todoItem.description
            binding.textViewTeamTodoAssignee.text = todoItem.assignee

            val creationDate = todoItem.creationTime.substring(0, 10)
            val creationTime = todoItem.creationTime.substring(11, 23)

            binding.textViewTeamTodoCreationDate.text = creationDate

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