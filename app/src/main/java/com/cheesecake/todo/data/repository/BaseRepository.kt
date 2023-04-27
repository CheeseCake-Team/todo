package com.cheesecake.todo.data.repository

import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseRepository {
    protected val compositeDisposable = CompositeDisposable()

    fun clearCompositeDisposable() {
        compositeDisposable.dispose()
    }
}