package com.example.courseapp.domain

import com.example.courseapp.presentation.login.UserMainInfo

interface UserRepository {
    suspend fun saveUser(user: UserMainInfo)
    suspend fun getUserById(id: String): UserMainInfo?
}