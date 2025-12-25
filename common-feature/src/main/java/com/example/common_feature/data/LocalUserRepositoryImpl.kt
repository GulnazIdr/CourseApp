package com.example.common_feature.data

import android.util.Log
import com.example.common_feature.data.dao.UserDao
import com.example.common_feature.data.mappers.UserMapper
import com.example.common_feature.domain.DataStoreRepository
import com.example.common_feature.domain.FetchedResult
import com.example.common_feature.domain.UserRepository
import com.example.common_feature.domain.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

class LocalUserRepositoryImpl @Singleton constructor(
    private val userDao: UserDao,
    private val dataStoreRepository: DataStoreRepository
): UserMapper(), UserRepository{

    private var _userList: List<User> = emptyList()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchUserCredentials()
        }
    }

    override suspend fun saveUser(user: User): FetchedResult<Boolean> {
        try {
            val userEntity = user.toUserEntity()
            userDao.addNewUser(userEntity)
            return FetchedResult.Success(true)
        }catch (e: Exception){
            Log.e("SAVE USER ERROR", "${e.message} ${e::class.simpleName}")
            return FetchedResult.Error(e.message.toString())
        }
    }

    override suspend fun getUserById(id: String): FetchedResult<User?> {
        return try {
            val fetched = userDao.getUserById(id)?.toUser()
            FetchedResult.Success(fetched)
        }catch (e: Exception){
            Log.e("GETTING USER ERROR", "${e.message} ${e::class.simpleName}")
            FetchedResult.Error(e.message.toString());
        }
    }

    override suspend fun getUsers(): FetchedResult<List<User>> {
        return try {
            val fetched = userDao.getUsers().map { it.toUser() }
            FetchedResult.Success(fetched)
        }catch (e: Exception){
            Log.e("GETTING USERS ERROR", "${e.message} ${e::class.simpleName}")
            FetchedResult.Error(e.message.toString())
        }
    }

    override var usersCredentials: List<User> = _userList
        get() = _userList

    suspend fun fetchUserCredentials(){
        _userList = dataStoreRepository.getSavedUsers()
    }

    override suspend fun getCurrentUser(): User? {
        return dataStoreRepository.getCurrentUser()
    }
}