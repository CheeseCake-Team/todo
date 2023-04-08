package com.cheesecake.todo.data.local

interface SharedPreferencesService {
    fun saveToken(value: String)
    fun getToken(): String?
    fun saveExpireDate(value: String)
    fun getExpireDate(): String?

    fun removeTokenAndExpireDate()

}
