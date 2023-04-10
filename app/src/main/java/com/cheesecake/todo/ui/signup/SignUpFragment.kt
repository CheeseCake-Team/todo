package com.cheesecake.todo.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.cheesecake.todo.R
import com.cheesecake.todo.data.network.NetworkServiceImpl
import com.cheesecake.todo.data.repository.identity.AuthRepositoryImpl
import com.cheesecake.todo.databinding.FragmentSignUpBinding
import com.cheesecake.todo.ui.base.BaseFragment
import com.cheesecake.todo.ui.login.LoginFragment

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(), SignUpView {
    override val bindingInflater: (LayoutInflater) -> FragmentSignUpBinding =
        FragmentSignUpBinding::inflate

    private lateinit var presenter: SignUpPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = SignUpPresenter(
            AuthRepositoryImpl(NetworkServiceImpl()),
        )
        presenter.attachView(this)
        addCallBacks()

    }

    private fun addCallBacks() {
        binding.buttonSignUp.setOnClickListener {

            if (!isNetworkAvailable()) showNoInternetScreen()
            else {
                presenter.signUp(
                    binding.editTextUserNameSignUp.text.toString(),
                    binding.editTextPasswordSignUp.text.toString(),
                    binding.editTextConfirmPasswordSignUp.text.toString()
                )
            }

        }

        binding.textViewLogin.setOnClickListener {
            loadLoginFragment()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun navigateToLoginScreen() {
        loadLoginFragment()
    }

    override fun showError(error: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

    }

    private fun loadLoginFragment() {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_activity, LoginFragment())
            commit()
        }
    }
}