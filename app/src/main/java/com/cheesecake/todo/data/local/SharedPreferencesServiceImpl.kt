package com.cheesecake.todo.data.local

import android.content.SharedPreferences

class SharedPreferencesServiceImpl(
    private var sharedPreferences: SharedPreferences) :
    SharedPreferencesService {
    override fun saveTokenAndExpireDate(token: String, expireDate: String) {
        sharedPreferences.edit().apply {
            putString(TOKEN, token).apply()
            putString(EXPIRY, expireDate).apply()
        }
    }

    override fun getToken() =
        sharedPreferences.getString(TOKEN, "")

    override fun getExpireDate() =
        sharedPreferences.getString(EXPIRY, "")

    private companion object {
        const val TOKEN = "token"
        const val EXPIRY = "expiry"
    }

}