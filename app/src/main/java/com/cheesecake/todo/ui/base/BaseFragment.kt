package com.cheesecake.todo.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.cheesecake.todo.R
import com.cheesecake.todo.ui.login.LoginFragment

abstract class BaseFragment<VB : ViewBinding, P : BasePresenter<*, *>> : Fragment() {

    protected abstract val bindingInflater: (LayoutInflater) -> VB
    protected abstract val presenter: P

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = bindingInflater(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun loadLoginFragment() {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_activity, LoginFragment())
            commit()
        }
    }

}