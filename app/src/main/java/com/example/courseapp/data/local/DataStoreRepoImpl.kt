package com.example.courseapp.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.courseapp.domain.DataStoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepoImpl @Inject constructor(
    private val dataStorePref: DataStore<Preferences>
): DataStoreRepository {
    val CURRENT_USER_ID = stringPreferencesKey("current_user_id")

    override suspend fun saveCurrentUserId(id: String) {
        dataStorePref.edit { pref ->
            pref.clear()
            pref[CURRENT_USER_ID] = id
        }
        dataStorePref.data.first()
    }

    override suspend fun getCurrentUserId(): String {
        return dataStorePref.data.map { pref ->
            pref[CURRENT_USER_ID] ?: ""
        }.first()
    }
}