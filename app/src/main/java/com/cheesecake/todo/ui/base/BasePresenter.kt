package com.cheesecake.todo.ui.base

import com.cheesecake.todo.data.repository.BaseRepository

abstract class BasePresenter<V>(val repository: BaseRepository, val view: V) {
    fun onDestroy() {
        repository.clearCompositeDisposable()
    }
}