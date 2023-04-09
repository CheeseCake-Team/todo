package com.cheesecake.todo.ui.sign_up

import android.view.LayoutInflater
import com.cheesecake.todo.databinding.FragmentSignUpBinding
import com.cheesecake.todo.ui.base.BaseFragment

class SignUpFragment:BaseFragment<FragmentSignUpBinding>(){
    override val bindingInflater: (LayoutInflater) -> FragmentSignUpBinding=
        FragmentSignUpBinding::inflate


}