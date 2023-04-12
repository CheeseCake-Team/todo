package com.cheesecake.todo.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewbinding.ViewBinding
import com.cheesecake.todo.R

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected abstract val bindingInflater: (LayoutInflater) -> VB

    private var _binding: VB? = null

    protected val binding: VB
        get() = _binding as VB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}