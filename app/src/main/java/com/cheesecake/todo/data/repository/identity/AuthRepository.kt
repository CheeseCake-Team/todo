package com.cheesecake.todo.data.repository.identity

interface AuthRepository {
    fun login(username: String, password: String, callback: AuthCallback)
    fun signUp(username: String, password: String, teamId: String, callback: AuthCallback)
}