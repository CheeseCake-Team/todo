package com.cheesecake.todo.ui.base

import androidx.recyclerview.widget.DiffUtil
import com.cheesecake.todo.data.models.TodoItem

class TodoDiffUtil(
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