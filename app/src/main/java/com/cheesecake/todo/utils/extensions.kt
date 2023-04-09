package com.cheesecake.todo.utils

import com.cheesecake.todo.data.local.SharedPreferencesService
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

fun isUserNameValid(username: String): Boolean {
    return if (username.length >= 4) {
        return true
    } else {
        username.isNotBlank()
    }
}

fun isPasswordValid(password: String): Boolean {
    return password.length > 8
}

