package com.cheesecake.todo.ui.home

import com.cheesecake.todo.data.models.HomeItem

interface HomeView {
    fun initHomeList(homeList: List<DataItem>)

    fun showError(message: String)

}