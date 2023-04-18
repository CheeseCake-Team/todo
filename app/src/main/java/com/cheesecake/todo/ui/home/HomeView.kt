package com.cheesecake.todo.ui.home

import com.cheesecake.todo.data.models.HomeItem

interface HomeView {
    fun initHomeList(homeItem: HomeItem)

    fun showError(message: String)

}