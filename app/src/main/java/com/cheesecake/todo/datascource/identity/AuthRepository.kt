package com.cheesecake.todo.datascource.identity

interface AuthRepository {
    fun login(username: String, password: String, callback: LoginCallback)
    fun signUp(username: String, password: String, teamId: String, callback: SignUpCallback)
}