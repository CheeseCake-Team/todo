package com.cheesecake.todo.data.network.identity

import com.cheesecake.todo.data.models.response.BaseResponse
import com.cheesecake.todo.data.models.response.LoginValue
import com.cheesecake.todo.data.models.response.SignUpValue
import com.cheesecake.todo.data.network.AuthorizationInterceptor
import com.cheesecake.todo.utils.makeObservable
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.core.Single
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class IdentityNetworkServiceImpl(private val okHttpClient: OkHttpClient) : IdentityNetworkService {

    override fun login(username: String, password: String): Single<BaseResponse<LoginValue>> {
        val credentials = Credentials.basic(username, password)

        val request = Request.Builder().url(LOGIN_ENDPOINT)
            .addHeader(AuthorizationInterceptor.AUTHORIZATION_HEADER, credentials).build()

        val type = object : TypeToken<BaseResponse<LoginValue>>() {}.type
        return okHttpClient.makeObservable(request, type)
    }

    override fun signUp(
        username: String, password: String, teamId: String
    ): Single<BaseResponse<SignUpValue>> {
        val formBody = FormBody.Builder().add("username", username).add("password", password)
            .add("teamId", teamId).build()

        val request = Request.Builder().url(SIGNUP_ENDPOINT).post(formBody).build()
        val type = object : TypeToken<BaseResponse<SignUpValue>>() {}.type

        return okHttpClient.makeObservable(request,type)
    }

    private companion object {
        const val LOGIN_ENDPOINT = "https://team-todo-62dmq.ondigitalocean.app/login"
        const val SIGNUP_ENDPOINT = "https://team-todo-62dmq.ondigitalocean.app/signup"
    }
}
