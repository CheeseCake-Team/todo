package com.cheesecake.todo.ui


import android.view.LayoutInflater
import com.cheesecake.todo.ui.base.BaseActivity
import com.cheesecake.todo.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate




}