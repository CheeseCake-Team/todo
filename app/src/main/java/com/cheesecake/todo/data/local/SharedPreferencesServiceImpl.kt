package com.cheesecake.todo.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.cheesecake.todo.utils.Constants.EXPIRY
import com.cheesecake.todo.utils.Constants.PREFS_NAME
import com.cheesecake.todo.utils.Constants.TOKEN


class SharedPreferencesServiceImpl(
    var sharedPreferences: SharedPreferences,
                                   context: Context) :
    SharedPreferencesService {

init {
    val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    sharedPreferences = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )


}


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