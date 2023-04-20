package com.cheesecake.todo.ui.taskDetails

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.data.repository.todos.TodoCallback
import com.cheesecake.todo.data.repository.todos.TodoRepository
import com.cheesecake.todo.ui.base.BasePresenter

class TaskDetailsPresenter(
    private val todoRepository: TodoRepository, private val taskDetailsView: TaskDetailsView
) : BasePresenter<TodoRepository, TaskDetailsView>(todoRepository, taskDetailsView), TodoCallback {


    fun updateState(todo: TodoItem, isPersonal: Boolean, newState: TodoState) {
        if (isPersonal) {
            todoRepository.updatePersonalTodoStatus(todo.id, newState, this)
        } else {
            todoRepository.updateTeamTodoStatus(todo.id, newState, this)
        }
    }

    override fun onSuccessTeamTodo(todos: List<TodoItem>?) {

    }

    override fun onSuccessPersonalTodo(todos: List<TodoItem>?) {

    }

    override fun onError(errorMessage: String) {
        taskDetailsView.showError(errorMessage)
    }
}