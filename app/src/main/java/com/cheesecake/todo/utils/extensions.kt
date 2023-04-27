package com.cheesecake.todo.utils

import android.util.Log
import android.widget.EditText
import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.network.NetworkInterceptor
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type

inline fun <reified T> OkHttpClient.makeObservable(request: Request, type: Type): Single<BaseResponse<T>> {
    return Single.create { emitter ->
        val call = this.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (e !is NetworkInterceptor.NoInternetException) {
                    emitter.onError(e)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val parsedResponse = Gson().parseResponse<T>(body ?: "",type)

                if (response.isSuccessful) {
                    emitter.onSuccess(parsedResponse)
                } else {
                    emitter.onError(Exception(parsedResponse.message))
                }
            }
        })

        emitter.setCancellable { call.cancel() }

    }
}
inline fun <reified T> Gson.parseResponse(response: String?, type: Type): BaseResponse<T> {
    return this.fromJson(response, type)
}

inline fun <reified T> Gson.parseResponse(response: String?): BaseResponse<T> {
    Log.d("TAG", "parseResponse: ${object : TypeToken<BaseResponse<T>>() {}.type}")
    return this.fromJson(response, object : TypeToken<BaseResponse<T>>() {}.type)
}


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