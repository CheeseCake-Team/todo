package com.cheesecake.todo.data.local

interface SharedPreferencesService {
    fun saveTokenAndExpireDate(token: String, expireDate: String)
    fun getToken(): String?
    fun getExpireDate(): String?
}
