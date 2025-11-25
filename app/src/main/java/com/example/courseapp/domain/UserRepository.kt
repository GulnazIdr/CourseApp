package com.example.courseapp.domain

import com.example.courseapp.domain.models.User

interface UserRepository {
    suspend fun saveUser(user: User): FetchedResult<Boolean>
    suspend fun getUserById(id: String): FetchedResult<User?>
}