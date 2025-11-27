package com.example.common_feature.domain

interface DataStoreRepository {
    suspend fun saveCurrentUserId(id: String)
    suspend fun getCurrentUserId(): String
}