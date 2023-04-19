package com.cheesecake.todo.ui.base

import com.cheesecake.todo.data.repository.BaseRepository

abstract class BasePresenter<R : BaseRepository, V>(val repository: R, val view: V)