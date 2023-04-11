package com.cheesecake.todo.data.repository.identity

interface AuthRepository {
    fun login(username: String, password: String, callback: AuthCallback)
}