package com.cheesecake.todo.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.cheesecake.todo.R
import com.cheesecake.todo.data.repository.identity.IdentityRepositoryFactory
import com.cheesecake.todo.databinding.FragmentSignUpBinding
import com.cheesecake.todo.ui.base.BaseFragment
import com.cheesecake.todo.utils.setFocusAndHint

class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpPresenter>(), SignUpView {
    override val bindingInflater: (LayoutInflater) -> FragmentSignUpBinding =
        FragmentSignUpBinding::inflate

    override val presenter by lazy {
        val identityRepositoryFactory = requireActivity().application as IdentityRepositoryFactory
        val identityRepository = identityRepositoryFactory.createAuthRepository()
        SignUpPresenter(identityRepository, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallBacks()
    }

    private fun addCallBacks() {

        val usernameLayout = binding.textInputUserNameSignUp
        val passwordLayout = binding.textInputPasswordSignUp
        val confirmPasswordLayout = binding.textInputConfirmPasswordSignUp

        setFocusAndHint(
            usernameLayout, binding.editTextUserNameSignUp, getString(R.string.username)
        )
        setFocusAndHint(
            passwordLayout, binding.editTextPasswordSignUp, getString(R.string.password)
        )
        setFocusAndHint(
            confirmPasswordLayout,
            binding.editTextConfirmPasswordSignUp,
            getString(R.string.confirm_password)
        )

        with(binding) {
            buttonSignUp.setOnClickListener {
                presenter.signUp(
                    editTextUserNameSignUp.text.toString(),
                    editTextPasswordSignUp.text.toString(),
                    editTextConfirmPasswordSignUp.text.toString()
                )
            }
            textViewLogin.setOnClickListener {
                loadLoginFragment()
            }
        }
    }


    override fun navigateToLoginScreen() {
        loadLoginFragment()
    }

    override fun showError(error: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

    }

}