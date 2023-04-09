package com.cheesecake.todo.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cheesecake.todo.R
import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.local.SharedPreferencesServiceImpl
import com.cheesecake.todo.data.network.NetworkServiceImpl
import com.cheesecake.todo.data.repository.identity.AuthRepositoryImpl
import com.cheesecake.todo.databinding.FragmentLoginBinding
import com.cheesecake.todo.ui.base.BaseFragment
import com.cheesecake.todo.utils.Constants.PREFS_NAME


class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginView {

    private lateinit var presenter: LoginPresenter
    private lateinit var sharedPreferencesService: SharedPreferencesService


    override val bindingInflater: (LayoutInflater) -> FragmentLoginBinding =
        FragmentLoginBinding::inflate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        sharedPreferencesService = SharedPreferencesServiceImpl(
            requireActivity().getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = LoginPresenter(
            AuthRepositoryImpl(NetworkServiceImpl.getInstance()),
            sharedPreferencesService
        )
        presenter.attachView(this)

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


    override fun navigateToHomeScreen() {
        // Navigate to home screen
    }

    override fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }
}
