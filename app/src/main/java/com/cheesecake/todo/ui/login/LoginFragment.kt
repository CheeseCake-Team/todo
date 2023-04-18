package com.cheesecake.todo.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.cheesecake.todo.R
import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.local.SharedPreferencesServiceImpl
import com.cheesecake.todo.data.network.NetworkServiceImpl
import com.cheesecake.todo.data.repository.identity.IdentityRepositoryFactory
import com.cheesecake.todo.databinding.FragmentLoginBinding
import com.cheesecake.todo.ui.base.BaseFragment
import com.cheesecake.todo.ui.signup.SignUpFragment
import com.cheesecake.todo.utils.Constants.PREFS_NAME


class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginView {

    private lateinit var presenter: LoginPresenter


    override val bindingInflater: (LayoutInflater) -> FragmentLoginBinding =
        FragmentLoginBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireActivity().application as IdentityRepositoryFactory
        val identityRepository = application.createAuthRepository()

        presenter = LoginPresenter(identityRepository)
        presenter.attachView(this)

        val usernameLayout = binding.textInputUserNameLogin
        val passwordLayout = binding.textInputPasswordLogin

        usernameLayout.isHintAnimationEnabled = false
        passwordLayout.isHintAnimationEnabled = false

        binding.editTextUserNameLogin.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                usernameLayout.hint = ""
            } else if (binding.editTextUserNameLogin.text.toString().isEmpty()) {
                usernameLayout.hint = getString(R.string.username)
            }
        }

        binding.editTextPasswordLogin.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                passwordLayout.hint = ""
            } else if (binding.editTextPasswordLogin.text.toString().isEmpty()) {
                passwordLayout.hint = getString(R.string.password)
            }
        }

        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUserNameLogin.text.toString().trim()
            val password = binding.editTextPasswordLogin.text.toString().trim()

            presenter.login(username, password)
        }
        binding.textViewSignUp.setOnClickListener {
            navigateToSignup()
            
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }


    override fun navigateToHomeScreen(username: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(
                requireContext(),username,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun navigateToSignup(){
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_activity,SignUpFragment())
            commit()
        }
    }

    override fun showError(error: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }
}
