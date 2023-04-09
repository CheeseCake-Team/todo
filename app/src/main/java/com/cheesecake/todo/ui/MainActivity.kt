package com.cheesecake.todo.ui

import android.os.Bundle
import android.view.LayoutInflater
import com.cheesecake.todo.R
import com.cheesecake.todo.databinding.ActivityMainBinding
import com.cheesecake.todo.ui.base.BaseActivity
import com.cheesecake.todo.ui.login.LoginFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, LoginFragment())
            .commit()
    }




}