package com.cheesecake.todo.data.repository.identity

interface IdentityRepository {
    fun login(username: String, password: String, loginCallback: LoginCallback)
    fun signUp(username: String, password: String, teamId: String, signUpCallback: SignUpCallback)

    fun saveTokenAndExpireDate(token: String, expireDate: String)

}