package com.cheesecake.todo.ui.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.cheesecake.todo.R

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected abstract val bindingInflater: (LayoutInflater) -> VB

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isNetworkAvailable()) {
            showNoInternetScreen()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && (
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    }

    protected fun showNoInternetScreen() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.no_internet_connection_title)
        builder.setMessage(R.string.no_internet_connection_message)
        builder.setCancelable(false)
        builder.setPositiveButton(R.string.retry) { _, _ ->
            if (!isNetworkAvailable()) showNoInternetScreen()
        }
        builder.show()
    }



}