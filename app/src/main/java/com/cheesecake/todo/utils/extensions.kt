package com.cheesecake.todo.utils

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.cheesecake.todo.data.local.SharedPreferencesService
import com.cheesecake.todo.data.network.ApiResult
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


fun isTokenValid(token: String, expiry: String) =
    token.isNotEmpty() && System.currentTimeMillis() / 1000 < formattedTime(expiry)

fun formattedTime(expiry: String) =
    SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }.parse(expiry)?.time?.div(1000) ?: 0

fun removeTokenAndExpiryIfTokenInvalid(sharedPreferencesService: SharedPreferencesService) {
    val token = sharedPreferencesService.getToken()!!
    val expiry = sharedPreferencesService.getExpireDate()!!
    if (isTokenValid(token, expiry)) {
        sharedPreferencesService.removeTokenAndExpireDate()
    }
}

fun isNetworkAvailable(connectivityManager: ConnectivityManager): Boolean {
    val network = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
    return networkCapabilities != null && (
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
}

fun isUsernameValid(username: String) =
    username.length > 3 && username.isNotBlank()

fun isPasswordValid(password: String) =
    password.length > 8

fun arePasswordsTheSame(password: String, confirmationPassword: String) =
    password == confirmationPassword

fun OkHttpClient.makeCall(
    request: Request,
    callback: (ApiResult) -> Unit
) {

    this.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            ApiResult.Failure(e.message ?: "Unknown error")
        }

        override fun onResponse(call: Call, response: Response) {
            val body = response.body?.string()
            if (response.isSuccessful) {
                callback(ApiResult.Success(body ?: ""))
            } else {
                callback(ApiResult.Failure(body ?: ""))

            }
        }
    })
}












