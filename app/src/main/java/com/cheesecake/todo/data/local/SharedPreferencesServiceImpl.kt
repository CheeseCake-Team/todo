package com.cheesecake.todo.data.local

import android.content.SharedPreferences
import com.cheesecake.todo.utils.Constants.EXPIRY
import com.cheesecake.todo.utils.Constants.TOKEN

class SharedPreferencesServiceImpl(private val sharedPreferences: SharedPreferences) :
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

}