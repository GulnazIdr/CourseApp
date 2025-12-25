package com.example.common_feature.data.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.common_feature.data.mappers.UserMapper
import com.example.common_feature.domain.DataStoreRepository
import com.example.common_feature.domain.models.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepoImpl @Inject constructor(
    private val dataStorePref: DataStore<Preferences>,
    private val userDataStorePref: DataStore<List<UserSerial>>,
    private val currentUserDataStorePref: DataStore<UserSerial>
): DataStoreRepository, UserMapper() {

    val IS_SERVICE_DIALOG_SHOWED = booleanPreferencesKey("service_dialog_state")

    override suspend fun saveUserToList(user: UserSerial) {
        try {
            userDataStorePref.updateData { currentList ->
                val userList = currentList.toMutableList()
                val exists = userList.filter{it.email == user.email}
                if (exists.isEmpty()) userList.add(user)
                userList
            }
        }catch (e: Exception){
            Log.e("ADDING USER TO LIST IN DATASTORE", "${e.message} ${e::class.simpleName}")
        }
    }

    override suspend fun getSavedUsers(): List<User>{
        return try {
            userDataStorePref.data.map { it.map { it.toUser() } }.first()
        }catch (e: Exception){
            Log.e("GETTING USER LIST FROM DATASTORE", "${e.message} ${e::class.simpleName}")
            emptyList<User>()
        }
    }

    override suspend fun setCurrentUser(user: UserSerial) {
        try {
            currentUserDataStorePref.updateData { currentUser ->
                currentUser.copy(user.email, user.password)
            }
        }catch (e: Exception){
            Log.e("SET CURRENT USER TO DATASTORE", "${e.message} ${e::class.simpleName}")
        }
    }

    override suspend fun getCurrentUser(): User? {
        return try {
            currentUserDataStorePref.data.map { it.toUser() }.first()
        }catch (e: Exception){
            Log.e("GET CURRENT USER FROM DATASTORE", "${e.message} ${e::class.simpleName}")
            null
        }
    }

    override suspend fun setShowedServiceDialog(isShowed: Boolean) {
        dataStorePref.edit { pref ->
            pref[IS_SERVICE_DIALOG_SHOWED] = isShowed
        }
    }

    override suspend fun getShowedServiceDialog(): Boolean {
        return try {
            dataStorePref.data.map { pref->
                pref[IS_SERVICE_DIALOG_SHOWED] == true
            }.first()
        }catch (e: Exception){
            Log.e("GETTING SERVICE STATE", "${e.message} ${e::class.simpleName}")
            false
        }
    }
}