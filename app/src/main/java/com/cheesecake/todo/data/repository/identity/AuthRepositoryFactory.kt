package com.cheesecake.todo.data.repository.identity

interface AuthRepositoryFactory {
    fun createAuthRepository(): AuthRepository
}
