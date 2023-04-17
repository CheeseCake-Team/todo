package com.cheesecake.todo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.databinding.FragmentHomeBinding
import com.cheesecake.todo.ui.base.BaseFragment

class HomeFragment() : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    
}