package com.example.common_feature.domain

import com.example.common_feature.data.datastore.UserSerial
import com.example.common_feature.domain.models.User

interface DataStoreRepository {
    suspend fun saveUserToList(user: UserSerial)
    suspend fun getSavedUsers(): List<User>

    suspend fun setShowedServiceDialog(isShowed: Boolean)
    suspend fun getShowedServiceDialog(): Boolean

    suspend fun setCurrentUser(user: UserSerial)
    suspend fun getCurrentUser(): User?
}