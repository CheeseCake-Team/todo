package com.cheesecake.todo.data.repository.identity

interface AuthRepository {
    fun login(username: String, password: String, callback: LoginCallback)
    fun signUp(username: String, password: String, teamId: String, callback: SignUpCallback)
}