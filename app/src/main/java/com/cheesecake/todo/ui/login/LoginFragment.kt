package com.cheesecake.todo.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cheesecake.todo.R
import com.cheesecake.todo.data.repository.identity.AuthRepositoryImpl
import com.cheesecake.todo.data.network.NetworkServiceImpl


class LoginFragment : Fragment(), LoginView {

    private lateinit var presenter: LoginPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = LoginPresenter(AuthRepositoryImpl(NetworkServiceImpl.getInstance()))
        presenter.attachView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }


    override fun navigateToHomeScreen(pair: Pair<String, String>) {
        // Navigate to home screen
    }

    override fun showError(error: Int) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }
}
