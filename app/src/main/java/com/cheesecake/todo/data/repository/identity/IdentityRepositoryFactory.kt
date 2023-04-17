package com.cheesecake.todo.data.repository.identity

interface IdentityRepositoryFactory {
    fun createAuthRepository(): IdentityRepository
}
