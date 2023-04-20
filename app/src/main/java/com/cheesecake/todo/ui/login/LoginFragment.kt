package com.cheesecake.todo.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.cheesecake.todo.R
import com.cheesecake.todo.data.repository.identity.IdentityRepositoryFactory
import com.cheesecake.todo.databinding.FragmentLoginBinding
import com.cheesecake.todo.ui.base.BaseFragment
import com.cheesecake.todo.ui.home.HomeFragment
import com.cheesecake.todo.ui.signup.SignUpFragment
import com.cheesecake.todo.utils.setFocusAndHint


class LoginFragment : BaseFragment<FragmentLoginBinding, LoginPresenter>(), LoginView {

    override val bindingInflater: (LayoutInflater) -> FragmentLoginBinding =
        FragmentLoginBinding::inflate

    override val presenter by lazy {
        val identityRepositoryFactory = requireActivity().application as IdentityRepositoryFactory
        val identityRepository = identityRepositoryFactory.createAuthRepository()
        LoginPresenter(identityRepository, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallBacks()
    }

    private fun addCallBacks() {

        with(binding) {
            textInputUserNameLogin.setFocusAndHint(
                editTextUserNameLogin, getString(R.string.username)
            )
            textInputUserNameLogin.setFocusAndHint(
                editTextPasswordLogin, getString(R.string.password)
            )
            buttonLogin.setOnClickListener {
                val username = editTextUserNameLogin.text.toString().trim()
                val password = editTextPasswordLogin.text.toString().trim()
                presenter.login(username, password)
            }
            textViewSignUp.setOnClickListener {
                navigateToSignup()
            }
        }
    }

    override fun navigateToHomeScreen() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_activity, HomeFragment()).commit()
    }

    private fun navigateToSignup() {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_activity, SignUpFragment())
            commit()
        }
    }

    override fun showError(error: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }
}