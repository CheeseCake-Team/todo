package com.cheesecake.todo.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.cheesecake.todo.R
import com.cheesecake.todo.ui.base.BaseActivity
import com.cheesecake.todo.databinding.ActivityMainBinding
import com.cheesecake.todo.ui.base.BaseFragment
import com.cheesecake.todo.ui.sign_up.SignUpFragment


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        initializeHomeScreen()
    }

    private fun loadFragmentIntoContainer(baseFragment: BaseFragment<*>) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_activity, baseFragment).commit()
    }

    fun initializeHomeScreen() {
        binding.bottomNavigationBar.visibility = View.GONE
        loadFragmentIntoContainer(SignUpFragment())

    }
}
