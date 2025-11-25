package com.example.courseapp.data.local

import android.util.Log
import com.example.courseapp.data.local.dao.UserDao
import com.example.courseapp.data.mappers.UserMapper
import com.example.courseapp.domain.FetchedResult
import com.example.courseapp.domain.UserRepository
import com.example.courseapp.domain.models.User
import javax.inject.Inject

class LocalUserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
): UserMapper(), UserRepository{

    override suspend fun saveUser(user: User): FetchedResult<Boolean> {
        try {
            userDao.addNewUser(user.toUserEntity())
            return FetchedResult.Success(true)
        }catch (e: Exception){
            Log.e("SAVE USER ERROR", "${e.message} ${e::class.simpleName}")
            return FetchedResult.Error(e.message.toString())
        }
    }

    override suspend fun getUserById(id: String): FetchedResult<User?> {
        return try {
            FetchedResult.Success(userDao.getUserById(id)?.toUser())
        }catch (e: Exception){
            Log.e("GETTING USER ERROR", "${e.message} ${e::class.simpleName}")
            FetchedResult.Error(e.message.toString());
        }
    }
}