package com.example.common_feature.data

import android.util.Log
import com.example.common_feature.data.dao.UserDao
import com.example.common_feature.data.mappers.UserMapper
import com.example.common_feature.domain.FetchedResult
import com.example.common_feature.domain.UserRepository
import com.example.common_feature.domain.models.User
import javax.inject.Inject

class LocalUserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
): UserMapper(), UserRepository{

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
}
