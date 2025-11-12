package com.example.courseapp.domain

interface DataStoreRepository {
    suspend fun saveCurrentUserId(id: String)
    suspend fun getCurrentUserId(): String
}