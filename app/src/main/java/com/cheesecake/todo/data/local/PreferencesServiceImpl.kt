package com.cheesecake.todo.data.local

import android.content.SharedPreferences
import com.cheesecake.todo.utils.Constants.EXPIRY
import com.cheesecake.todo.utils.Constants.TOKEN

class PreferencesServiceImpl(private val sharedPreferences: SharedPreferences) :
    SharedPreferencesService {
    override fun saveToken(value: String) =
        sharedPreferences.edit().putString(TOKEN, value).apply()


    override fun getToken() =
        sharedPreferences.getString(TOKEN, "")


    override fun saveExpireDate(value: String) =
        sharedPreferences.edit().putString(EXPIRY, value).apply()


    override fun getExpireDate() =
        sharedPreferences.getString(EXPIRY, "")


    override fun removeTokenAndExpireDate() {
        sharedPreferences.edit().apply {
            remove(TOKEN)
            remove(EXPIRY)
        }.apply()
    }


}