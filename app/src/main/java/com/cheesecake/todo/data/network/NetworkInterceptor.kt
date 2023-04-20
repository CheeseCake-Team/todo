package com.cheesecake.todo.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkInterceptor(private val context: Context) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkAvailable()) {

            showNoInternetConnectionDialog()

            throw NoInternetException("No internet connection")
        }
        return chain.proceed(chain.request())
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    private fun showNoInternetConnectionDialog() {
        Handler(Looper.getMainLooper()).post {
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again.")
                .setPositiveButton("OK", null)
                .create()
            alertDialog.show()
        }
    }


    class NoInternetException(message: String) : IOException(message)
}
