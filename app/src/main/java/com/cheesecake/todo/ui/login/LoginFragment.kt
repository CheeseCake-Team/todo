package com.cheesecake.todo.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.local.SharedPreferencesServiceImpl
import com.cheesecake.todo.data.network.NetworkServiceImpl
import com.cheesecake.todo.data.repository.identity.AuthRepositoryFactory
import com.cheesecake.todo.data.repository.identity.AuthRepositoryImpl
import com.cheesecake.todo.databinding.FragmentLoginBinding
import com.cheesecake.todo.ui.base.BaseFragment
import com.cheesecake.todo.utils.Constants.PREFS_NAME


class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginView {

    private lateinit var presenter: LoginPresenter
    private lateinit var sharedPreferencesService: SharedPreferencesService


    override val bindingInflater: (LayoutInflater) -> FragmentLoginBinding =
        FragmentLoginBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireActivity().application as AuthRepositoryFactory
        val authRepository = application.createAuthRepository()

        presenter = LoginPresenter(authRepository)

        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUserNameLogin.text.toString().trim()
            val password = binding.editTextPasswordLogin.text.toString().trim()

            presenter.login(username, password)
        }
        binding.textViewSignUp.setOnClickListener {


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }


    override fun navigateToHomeScreen(username: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(
                requireContext(),
                sharedPreferencesService.getToken(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun showError(error: String) {
        requireActivity().runOnUiThread {
            sharedPreferencesService.getToken()
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }
}
