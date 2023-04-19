package com.cheesecake.todo.ui.taskDetails

import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.data.repository.todos.TodoRepository

class TaskDetailsPresenter(private val todoRepository: TodoRepository) {
    private var taskDetailsView: TaskDetailsView? = null

    fun attachView(view: TaskDetailsView) {
        taskDetailsView = view


    }

    fun detachView() {
        taskDetailsView = null
    }

    fun updateState(todo: TodoItem, isPersonal: Boolean, newState: TodoState) {
        if (isPersonal) {
//            todoRepository.changePersonalTodoStatus(todo.id, newState, )
        } else {
//            todoRepository.changeTeamTodoStatus(todo.id, newState,)
        }
    }
}