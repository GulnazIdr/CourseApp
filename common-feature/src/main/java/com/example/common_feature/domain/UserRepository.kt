package com.example.common_feature.domain

import com.example.common_feature.domain.models.User

interface UserRepository {
    suspend fun saveUser(user: User): FetchedResult<Boolean>
    suspend fun getUserById(id: String): FetchedResult<User?>
    suspend fun getUsers(): FetchedResult<List<User>>
    val usersCredentials: List<User>
    suspend fun getCurrentUser(): User?
}