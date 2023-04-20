package com.cheesecake.todo.utils

import android.widget.EditText
import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.network.NetworkInterceptor
import com.cheesecake.todo.data.network.ResponseCallback
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException


inline fun <reified T> OkHttpClient.makeCall(
    request: Request,
    responseCallback: ResponseCallback,
) {
    this.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            if (e is NetworkInterceptor.NoInternetException) {
                // Do nothing here because the dialog has already been shown
            } else {
                responseCallback.onFail(e.toString())
            }
        }

        override fun onResponse(call: Call, response: Response) {
            val body = response.body?.string()
            val parsedResponse = Gson().parseResponse<T>(body ?: "")
            responseCallback.onSuccess(parsedResponse)
        }
    })
}


inline fun <reified T> Gson.parseResponse(response: String?): BaseResponse<T> =
    this.fromJson(response, object : TypeToken<BaseResponse<T>>() {}.type)


fun TextInputLayout.setFocusAndHint(editText: EditText, hint: String) {
    isHintAnimationEnabled = false
    editText.setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            this.hint = ""
        } else if (editText.text.toString().isEmpty()) {
            this.hint = hint
        }
    }
}






