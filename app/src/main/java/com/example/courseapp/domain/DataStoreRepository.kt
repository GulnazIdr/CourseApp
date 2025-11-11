package com.example.courseapp.domain

interface DataStoreRepository {
    suspend fun saveCurrentUserId(id: String)
    fun getCurrentUserId(): String
}