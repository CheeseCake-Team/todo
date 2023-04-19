package com.cheesecake.todo.utils

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.network.ResponseCallback
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

fun isNetworkAvailable(connectivityManager: ConnectivityManager): Boolean {
    val network = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
    return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(
        NetworkCapabilities.TRANSPORT_CELLULAR
    ) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
}

inline fun <reified T> OkHttpClient.makeCall(
    request: Request,
    responseCallback: ResponseCallback,
) {

    this.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            responseCallback.onFail(e.message ?: "Unknown error")
        }

        override fun onResponse(call: Call, response: Response) {
            val body = response.body?.string()
            if (response.isSuccessful) {
                val parsedResponse = Gson().parseResponse<T>(body ?: "")
                responseCallback.onSuccess(parsedResponse)
            } else {
                responseCallback.onFail(body ?: "")

            }
        }
    })
}


inline fun <reified T> Gson.parseResponse(response: String?): BaseResponse<T> =
    this.fromJson(response, object : TypeToken<BaseResponse<T>>() {}.type)



fun View.makeGone(){
    this.visibility=View.GONE
}
fun View.makeVisible(){
    this.visibility=View.VISIBLE
}





