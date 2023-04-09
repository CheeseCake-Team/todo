package com.cheesecake.todo.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cheesecake.todo.R
import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.local.SharedPreferencesServiceImpl
import com.cheesecake.todo.data.network.NetworkServiceImpl
import com.cheesecake.todo.data.repository.identity.AuthRepositoryImpl
import com.cheesecake.todo.utils.Constants.PREFS_NAME


class LoginFragment : Fragment(), LoginView {

    private lateinit var presenter: LoginPresenter
    private lateinit var sharedPreferencesService: SharedPreferencesService

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
            AuthRepositoryImpl(NetworkServiceImpl()),
            sharedPreferencesService
        )
        presenter.attachView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }


    override fun navigateToHomeScreen() {
        // Navigate to home screen
    }

    override fun showError(error: Int) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }
}
